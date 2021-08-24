package pers.syq.fastadmin.backstage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import pers.syq.fastadmin.backstage.common.utils.PageUtils;
import pers.syq.fastadmin.backstage.entity.IdCountEntity;
import pers.syq.fastadmin.backstage.entity.SysRoleMenuEntity;

import java.util.List;
import java.util.Map;

/**
 *
 * @author syq
 * @email sunyuqian0211@gmail.com
 * @date 2021-08-16
 */
public interface SysRoleMenuService extends IService<SysRoleMenuEntity> {
    PageUtils queryPage(Map<String, Object> params);

    void saveRoleMenus(List<Long> menuIdList, Long roleId);

    void removeByRoleId(Long roleId);

    void removeByRoleIds(List<Long> roleIds);

    void removeByMenuIds(List<Long> menuIds);

    void removeByMenuId(Long menuId);

    List<IdCountEntity> listIdCount(List<Long> peerMenuIds);
}

