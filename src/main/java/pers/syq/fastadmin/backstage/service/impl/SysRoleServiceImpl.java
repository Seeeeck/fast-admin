package pers.syq.fastadmin.backstage.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pers.syq.fastadmin.backstage.common.utils.PageUtils;
import pers.syq.fastadmin.backstage.common.utils.RedisUtils;
import pers.syq.fastadmin.backstage.common.utils.SecurityUtils;
import pers.syq.fastadmin.backstage.entity.dto.RoleDTO;
import pers.syq.fastadmin.backstage.entity.SysMenuEntity;
import pers.syq.fastadmin.backstage.entity.SysRoleEntity;
import pers.syq.fastadmin.backstage.entity.SysRoleMenuEntity;
import pers.syq.fastadmin.backstage.entity.SysUserRoleEntity;
import pers.syq.fastadmin.backstage.mapper.SysRoleMapper;
import pers.syq.fastadmin.backstage.service.SysMenuService;
import pers.syq.fastadmin.backstage.service.SysRoleMenuService;
import pers.syq.fastadmin.backstage.service.SysRoleService;
import pers.syq.fastadmin.backstage.service.SysUserRoleService;
import pers.syq.fastadmin.backstage.entity.vo.RoleMenuVO;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


@Service("sysRoleService")
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRoleEntity> implements SysRoleService {

    @Autowired
    private SysRoleMenuService roleMenuService;

    @Autowired
    private SysMenuService menuService;

    @Autowired
    private SysUserRoleService userRoleService;

    @Autowired
    private RedisUtils redisUtils;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String roleName = (String)params.get("roleName");
        LambdaQueryWrapper<SysRoleEntity> wrapper = new LambdaQueryWrapper<>();
        if (StrUtil.isNotEmpty(roleName)){
            wrapper.like(SysRoleEntity::getRoleName,roleName);
        }
        IPage<SysRoleEntity> page = this.page(PageUtils.getPage(params),wrapper);
        return new PageUtils(page);
    }

    @Override
    public List<SysRoleEntity> listByUserId(Long id) {
        return this.baseMapper.selectListByUserId(id);
    }

    @Transactional
    @Override
    public void saveRoleDTO(RoleDTO roleDTO) {
        SysRoleEntity roleEntity = new SysRoleEntity();
        BeanUtil.copyProperties(roleDTO,roleEntity);
        SecurityUtils.getUserId().ifPresent(roleEntity::setCreateUserId);
        this.save(roleEntity);
        Set<Long> menuIdList = roleDTO.getMenuIds();
        if (CollectionUtil.isNotEmpty(menuIdList)){
            roleMenuService.saveRoleMenus(menuIdList,roleEntity.getId());
        }
    }

    @Transactional
    @Override
    public void updateRoleDTO(RoleDTO roleDTO) {
        SysRoleEntity roleEntity = new SysRoleEntity();
        BeanUtil.copyProperties(roleDTO,roleEntity);
        this.updateById(roleEntity);
        List<SysRoleMenuEntity> roleMenuEntities = roleMenuService.listByRoleId(roleEntity.getId());
        HashSet<Long> menuIds = new HashSet<>();
        for (SysRoleMenuEntity roleMenuEntity : roleMenuEntities) {
            menuIds.add(roleMenuEntity.getMenuId());
        }
        Set<Long> dtoMenuIds = roleDTO.getMenuIds();
        if(!menuIds.equals(dtoMenuIds)){
            List<Long> userIds = userRoleService
                    .listByRoleId(roleEntity.getId())
                    .stream().map(SysUserRoleEntity::getUserId).collect(Collectors.toList());
            redisUtils.deleteTokenByUserIds(userIds);
            roleMenuService.removeByRoleId(roleDTO.getId());
            if (CollectionUtil.isNotEmpty(dtoMenuIds)){
                roleMenuService.saveRoleMenus(dtoMenuIds,roleEntity.getId());
            }
        }
    }

    @Override
    public RoleMenuVO getRoleMenuVO(Long id) {
        SysRoleEntity roleEntity = this.getById(id);
        RoleMenuVO roleMenuVO = new RoleMenuVO();
        BeanUtil.copyProperties(roleEntity,roleMenuVO);
        List<SysMenuEntity> menus = menuService.listByRoleId(id);
        if (CollectionUtil.isNotEmpty(menus)){
            List<Long> menuIdList = menus.stream().map(SysMenuEntity::getId).collect(Collectors.toList());
            roleMenuVO.setMenuIdList(menuIdList);
        }
        return roleMenuVO;
    }


    @Transactional
    @Override
    public void removeBatch(List<Long> ids) {
        this.removeByIds(ids);
        List<Long> userIds = userRoleService
                .listByRoleIds(ids)
                .stream().map(SysUserRoleEntity::getUserId).collect(Collectors.toList());
        redisUtils.deleteTokenByUserIds(userIds);
        roleMenuService.removeByRoleIds(ids);
        userRoleService.removeByRoleIds(ids);
    }

    @Override
    public int countAdminByUserId(Long userId) {
        return this.baseMapper.selectCountAdminByUserId(userId);
    }


}