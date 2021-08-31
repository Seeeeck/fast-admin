package pers.syq.fastadmin.backstage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import pers.syq.fastadmin.backstage.entity.IdCountEntity;
import pers.syq.fastadmin.backstage.entity.SysRoleMenuEntity;

import java.util.Collection;
import java.util.List;

/**
 *
 * @author syq
 * @email sunyuqian0211@gmail.com
 * @date 2021-08-16
 */
public interface SysRoleMenuService extends IService<SysRoleMenuEntity> {

    void saveRoleMenus(Collection<Long> menuIdList, Long roleId);

    void removeByRoleId(Long roleId);

    void removeByRoleIds(Collection<Long> roleIds);

    void removeByMenuIds(Collection<Long> menuIds);

    void removeByMenuId(Long menuId);

    List<IdCountEntity> listIdCount(List<Long> peerMenuIds);

    List<SysRoleMenuEntity> listByRoleId(Long roleId);
}

