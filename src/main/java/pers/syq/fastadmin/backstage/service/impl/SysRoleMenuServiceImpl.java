package pers.syq.fastadmin.backstage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import pers.syq.fastadmin.backstage.common.utils.PageUtils;
import pers.syq.fastadmin.backstage.entity.IdCountEntity;
import pers.syq.fastadmin.backstage.entity.SysRoleMenuEntity;
import pers.syq.fastadmin.backstage.mapper.SysRoleMenuMapper;
import pers.syq.fastadmin.backstage.service.SysRoleMenuService;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service("sysRoleMenuService")
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper, SysRoleMenuEntity> implements SysRoleMenuService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SysRoleMenuEntity> page = this.page(PageUtils.getPage(params));
        return new PageUtils(page);
    }

    @Override
    public void saveRoleMenus(List<Long> menuIdList, Long roleId) {
        List<SysRoleMenuEntity> entities = menuIdList.stream().map(menuId -> new SysRoleMenuEntity(null, roleId, menuId)).collect(Collectors.toList());
        this.saveBatch(entities);
    }

    @Override
    public void removeByRoleId(Long roleId) {
        this.remove(new LambdaQueryWrapper<SysRoleMenuEntity>().eq(SysRoleMenuEntity::getRoleId,roleId));
    }

    @Override
    public void removeByRoleIds(List<Long> roleIds) {
        this.remove(new LambdaQueryWrapper<SysRoleMenuEntity>().in(SysRoleMenuEntity::getRoleId,roleIds));
    }

    @Override
    public void removeByMenuIds(List<Long> menuIds) {
        this.remove(new LambdaQueryWrapper<SysRoleMenuEntity>().in(SysRoleMenuEntity::getMenuId,menuIds));
    }

    @Override
    public void removeByMenuId(Long menuId) {
        this.remove(new LambdaQueryWrapper<SysRoleMenuEntity>().eq(SysRoleMenuEntity::getMenuId,menuId));
    }

    @Override
    public List<IdCountEntity> listIdCount(List<Long> peerMenuIds) {
        return this.baseMapper.listIdCount(peerMenuIds);
    }

}