package pers.syq.fastadmin.backstage.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNode;
import cn.hutool.core.lang.tree.TreeNodeConfig;
import cn.hutool.core.lang.tree.TreeUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pers.syq.fastadmin.backstage.common.exception.BaseException;
import pers.syq.fastadmin.backstage.common.exception.ErrorCode;
import pers.syq.fastadmin.backstage.common.utils.PageUtils;
import pers.syq.fastadmin.backstage.entity.IdCountEntity;
import pers.syq.fastadmin.backstage.entity.SysMenuEntity;
import pers.syq.fastadmin.backstage.entity.SysRoleMenuEntity;
import pers.syq.fastadmin.backstage.mapper.SysMenuMapper;
import pers.syq.fastadmin.backstage.service.SysMenuService;
import pers.syq.fastadmin.backstage.service.SysRoleMenuService;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service("sysMenuService")
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenuEntity> implements SysMenuService {
    @Autowired
    private SysRoleMenuService roleMenuService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SysMenuEntity> page = this.page(PageUtils.getPage(params));
        return new PageUtils(page);
    }

    @Override
    public List<SysMenuEntity> listByUserId(Long userId) {
        return this.baseMapper.selectListByUserId(userId);
    }

    @Override
    public List<Tree<Long>> listMenusForTree(String option,Boolean noButtonType) {
        List<SysMenuEntity> menuEntities = this.list();
        if (CollectionUtil.isNotEmpty(menuEntities)){
            List<SysMenuEntity> menuList;
            if (noButtonType != null && noButtonType){
                menuList = menuEntities.stream().filter(menu -> menu.getType() != 2).collect(Collectors.toList());
            }else {
                menuList = menuEntities;
            }
            TreeNodeConfig treeNodeConfig = new TreeNodeConfig();
            treeNodeConfig.setWeightKey("orderNum");
            if ("less".equalsIgnoreCase(option)){
                List<TreeNode<Long>> treeNodes = menuList.stream()
                        .map(menu -> new TreeNode<>(menu.getId(), menu.getParentId(), menu.getName(), menu.getOrderNum()))
                        .collect(Collectors.toList());
                return TreeUtil.build(treeNodes,0L,treeNodeConfig, BeanUtil::copyProperties);
            }else if ("all".equalsIgnoreCase(option)){
                List<TreeNode<Long>> treeNodes = menuList.stream()
                        .map(menu -> {
                            TreeNode<Long> treeNode = new TreeNode<>(menu.getId(), menu.getParentId(), menu.getName(), menu.getOrderNum());
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
                            treeNode.setExtra(map);
                            return treeNode;
                        })
                        .collect(Collectors.toList());
                return TreeUtil.build(treeNodes,0L,treeNodeConfig, (treeNode,tree) -> {
                    tree.setId(treeNode.getId());
                    tree.setName(treeNode.getName());
                    tree.setParentId(treeNode.getParentId());
                    tree.setWeight(treeNode.getWeight());
                    tree.putAll(treeNode.getExtra());
                });
            }

        }
        return null;
    }

    @Override
    public List<SysMenuEntity> listByRoleId(Long roleId) {
        return this.baseMapper.selectListByRoleId(roleId);
    }

    @Transactional
    @Override
    public void removeBatch(List<Long> ids) {
        this.removeByIds(ids);
        roleMenuService.removeByMenuIds(ids);
    }

    @Override
    public SysMenuEntity getParentById(Long id) {
        return this.baseMapper.selectParentById(id);
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
            List<SysMenuEntity> peers = this.list(new LambdaQueryWrapper<SysMenuEntity>().eq(SysMenuEntity::getParentId,menuEntity.getParentId()));
            List<Long> peerIds = peers.stream().map(SysMenuEntity::getId).collect(Collectors.toList());
            List<IdCountEntity> idCountEntities = roleMenuService.listIdCount(peerIds);
            List<Long> roleIdList = idCountEntities.stream().filter(idCount -> idCount.getCount() == 1).map(IdCountEntity::getId).collect(Collectors.toList());
            if (CollectionUtil.isNotEmpty(roleIdList)){
                roleMenuService.remove(new LambdaQueryWrapper<SysRoleMenuEntity>()
                        .eq(SysRoleMenuEntity::getMenuId,menuEntity.getParentId())
                        .in(SysRoleMenuEntity::getRoleId,roleIdList));
            }
        }
        roleMenuService.removeByMenuId(menuId);
        return SqlHelper.retBool(this.getBaseMapper().deleteById(id));

    }


    private List<SysMenuEntity> listPeersById(Long id){
        return this.baseMapper.selectPeerListById(id);
    }
}