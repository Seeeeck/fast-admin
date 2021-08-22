package pers.syq.fastadmin.backstage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import pers.syq.fastadmin.backstage.common.utils.PageUtils;
import pers.syq.fastadmin.backstage.entity.SysUserRoleEntity;
import pers.syq.fastadmin.backstage.mapper.SysUserRoleMapper;
import pers.syq.fastadmin.backstage.service.SysUserRoleService;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service("sysUserRoleService")
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRoleEntity> implements SysUserRoleService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SysUserRoleEntity> page = this.page(PageUtils.getPage(params));
        return new PageUtils(page);
    }

    @Override
    public void saveUserRoles(List<Long> roleIdList, Long userId) {
        List<SysUserRoleEntity> userRoleEntities = roleIdList.stream().map(roleId -> {
            SysUserRoleEntity entity = new SysUserRoleEntity();
            entity.setUserId(userId);
            entity.setRoleId(roleId);
            return entity;
        }).collect(Collectors.toList());
        this.saveBatch(userRoleEntities);
    }

    @Override
    public void removeByUserIds(List<Long> userIds) {
        this.remove(new LambdaQueryWrapper<SysUserRoleEntity>().in(SysUserRoleEntity::getUserId,userIds));
    }

    @Override
    public void removeByRoleIds(List<Long> roleIds) {
        this.remove(new LambdaQueryWrapper<SysUserRoleEntity>().in(SysUserRoleEntity::getRoleId,roleIds));
    }


}