package pers.syq.fastadmin.backstage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import pers.syq.fastadmin.backstage.entity.SysUserRoleEntity;
import pers.syq.fastadmin.backstage.mapper.SysUserRoleMapper;
import pers.syq.fastadmin.backstage.service.SysUserRoleService;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


@Service("sysUserRoleService")
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRoleEntity> implements SysUserRoleService {

    @Override
    public void saveUserRoles(Collection<Long> roleIdList, Long userId) {
        List<SysUserRoleEntity> userRoleEntities = roleIdList.stream().map(roleId -> {
            SysUserRoleEntity entity = new SysUserRoleEntity();
            entity.setUserId(userId);
            entity.setRoleId(roleId);
            return entity;
        }).collect(Collectors.toList());
        this.saveBatch(userRoleEntities);
    }

    @Override
    public void removeByUserIds(Collection<Long> userIds) {
        this.remove(new LambdaQueryWrapper<SysUserRoleEntity>().in(SysUserRoleEntity::getUserId,userIds));
    }

    @Override
    public void removeByRoleIds(Collection<Long> roleIds) {
        this.remove(new LambdaQueryWrapper<SysUserRoleEntity>().in(SysUserRoleEntity::getRoleId,roleIds));
    }

    @Override
    public List<Long> listUserIdsByMenuId(Long menuId) {
        return this.baseMapper.selectUserIdsByMenuId(menuId);
    }

    @Override
    public List<SysUserRoleEntity> listByRoleId(Long roleId) {
        return this.list(new LambdaQueryWrapper<SysUserRoleEntity>().eq(SysUserRoleEntity::getRoleId, roleId));
    }

    @Override
    public List<SysUserRoleEntity> listByRoleIds(List<Long> roleIds) {
        return this.list(new LambdaQueryWrapper<SysUserRoleEntity>().in(SysUserRoleEntity::getRoleId, roleIds));
    }

    @Override
    public List<SysUserRoleEntity> listByUserId(Long userId) {
        return this.list(new LambdaQueryWrapper<SysUserRoleEntity>().eq(SysUserRoleEntity::getUserId,userId));
    }

    @Override
    public void removeByUserId(Long userId) {
        this.remove(new LambdaQueryWrapper<SysUserRoleEntity>().eq(SysUserRoleEntity::getUserId,userId));
    }


}