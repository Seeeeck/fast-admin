package pers.syq.fastadmin.backstage.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNode;
import cn.hutool.core.lang.tree.TreeNodeConfig;
import cn.hutool.core.lang.tree.TreeUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pers.syq.fastadmin.backstage.common.exception.BaseException;
import pers.syq.fastadmin.backstage.common.exception.ErrorCode;
import pers.syq.fastadmin.backstage.common.utils.RedisUtils;
import pers.syq.fastadmin.backstage.dto.MenuDTO;
import pers.syq.fastadmin.backstage.entity.IdCountEntity;
import pers.syq.fastadmin.backstage.entity.SysMenuEntity;
import pers.syq.fastadmin.backstage.entity.SysRoleMenuEntity;
import pers.syq.fastadmin.backstage.mapper.SysMenuMapper;
import pers.syq.fastadmin.backstage.service.SysMenuService;
import pers.syq.fastadmin.backstage.service.SysRoleMenuService;
import pers.syq.fastadmin.backstage.service.SysUserRoleService;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service("sysMenuService")
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenuEntity> implements SysMenuService {
    @Autowired
    private SysRoleMenuService roleMenuService;

    @Autowired
    private SysUserRoleService userRoleService;

    @Autowired
    private RedisUtils redisUtils;


    @Override
    public List<SysMenuEntity> listByUserId(Long userId) {
        return this.baseMapper.selectListByUserId(userId);
    }

    @Override
    public List<Tree<Long>> listMenusForTree(String option,Boolean noButtonType) {
        List<SysMenuEntity> menuEntities = this.list();
        if (CollectionUtil.isEmpty(menuEntities)){
            return Collections.emptyList();
        }
        List<SysMenuEntity> menuList;
        if (noButtonType != null && noButtonType){
            menuList = menuEntities.stream().filter(menu -> menu.getType() != 2).collect(Collectors.toList());
        }else {
            menuList = menuEntities;
        }
        TreeNodeConfig treeNodeConfig = new TreeNodeConfig();
        treeNodeConfig.setWeightKey("orderNum");
        List<TreeNode<Long>> treeNodes = menuList.stream()
                .map(menu -> {
                    TreeNode<Long> treeNode = new TreeNode<>(menu.getId(), menu.getParentId(), menu.getName(), menu.getOrderNum());
                    if ("all".equalsIgnoreCase(option)){
                        Map<String, Object> map = generateExtraMap(menu, menuList);
                        treeNode.setExtra(map);
                    }else {
                        treeNode.setExtra(Collections.emptyMap());
                    }
                    return treeNode;
                })
                .collect(Collectors.toList());
        return TreeUtil.build(treeNodes,0L,treeNodeConfig, this::copyTreeNodeProps);
    }

    private void copyTreeNodeProps(TreeNode<Long> treeNode, Tree<Long> tree) {
        tree.setId(treeNode.getId());
        tree.setName(treeNode.getName());
        tree.setParentId(treeNode.getParentId());
        tree.setWeight(treeNode.getWeight());
        tree.putAll(treeNode.getExtra());
    }

    private Map<String,Object> generateExtraMap(SysMenuEntity menu,List<SysMenuEntity> menuList){
        HashMap<String, Object> map = new HashMap<>();
        map.put("icon",menu.getIcon());
        map.put("type",menu.getType());
        map.put("path",menu.getPath());
        map.put("perms",menu.getPerms());
        if (menu.getParentId().equals(0L)){
            map.put("parentName","");
        }else {
            for (SysMenuEntity menuEntity : menuList) {
                if (menuEntity.getId().equals(menu.getParentId())){
                    map.put("parentName",menuEntity.getName());
                    break;
                }
            }
        }
        return map;
    }


    @Override
    public List<SysMenuEntity> listByRoleId(Long roleId) {
        return this.baseMapper.selectListByRoleId(roleId);
    }


    @Override
    public SysMenuEntity getParentById(Long id) {
        return this.baseMapper.selectParentById(id);
    }


    @Override
    public void saveDTO(MenuDTO menuDTO) {
        Integer type = menuDTO.getType();
        SysMenuEntity menuEntity = new SysMenuEntity();
        BeanUtil.copyProperties(menuDTO,menuEntity);
        if (type == 0 || type == 1){
            if (StrUtil.isBlank(menuDTO.getPath())){
                throw new BaseException(ErrorCode.VALID_EXCEPTION);
            }
            menuEntity.setPerms(null);
        }
        this.save(menuEntity);
    }

    @Override
    public void updateDTO(MenuDTO menuDTO) {
        Integer type = menuDTO.getType();
        SysMenuEntity menuEntity = new SysMenuEntity();
        BeanUtil.copyProperties(menuDTO,menuEntity);
        if (type == 0 || type == 1){
            if (StrUtil.isBlank(menuDTO.getPath())){
                throw new BaseException(ErrorCode.VALID_EXCEPTION);
            }
            menuEntity.setPerms(null);
        }else {
            SysMenuEntity entity = this.getById(menuDTO.getId());
            if (!entity.getPerms().equals(menuDTO.getPerms())){
                List<Long> userIds = userRoleService.listUserIdsByMenuId(entity.getId());
                if (!userIds.isEmpty()){
                    redisUtils.deleteTokenByUserIds(userIds);
                }
            }
        }
        this.updateById(menuEntity);
    }

    //TODO check
    @Transactional
    @Override
    public boolean removeById(Serializable id) {
        int count = this.count(new LambdaQueryWrapper<SysMenuEntity>().eq(SysMenuEntity::getParentId, id));
        if (count > 0){
            throw new BaseException(ErrorCode.FORBIDDEN);
        }
        Long menuId = (Long)id;
        SysMenuEntity menuEntity = this.getById(id);
        if (menuEntity.getType() == 1){
            // 获取与此菜单同父的所有子菜单(包括自身)
            List<SysMenuEntity> peers = this.list(new LambdaQueryWrapper<SysMenuEntity>().eq(SysMenuEntity::getParentId,menuEntity.getParentId()));
            List<Long> peerIds = peers.stream().map(SysMenuEntity::getId).collect(Collectors.toList());
            // 获取所有角色和子菜单的关联数
            List<IdCountEntity> idCountEntities = roleMenuService.listIdCount(peerIds);
            // 获取与子菜单关联数为1的所有角色
            List<Long> roleIdList = idCountEntities.stream().filter(idCount -> idCount.getCount() == 1).map(IdCountEntity::getId).collect(Collectors.toList());
            if (CollectionUtil.isNotEmpty(roleIdList)){
                // 删除上述角色与父菜单的关联
                roleMenuService.remove(new LambdaQueryWrapper<SysRoleMenuEntity>()
                        .eq(SysRoleMenuEntity::getMenuId,menuEntity.getParentId())
                        .in(SysRoleMenuEntity::getRoleId,roleIdList));
            }
        } else if (menuEntity.getType() == 2){
            List<Long> userIds = userRoleService.listUserIdsByMenuId(menuEntity.getId());
            if (!userIds.isEmpty()){
                redisUtils.deleteTokenByUserIds(userIds);
            }
        }
        roleMenuService.removeByMenuId(menuId);
        return SqlHelper.retBool(this.getBaseMapper().deleteById(id));

    }

    
}