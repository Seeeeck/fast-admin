package pers.syq.fastadmin.backstage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import pers.syq.fastadmin.backstage.entity.SysUserRoleEntity;

import java.util.Collection;
import java.util.List;

/**
 *
 * @author syq
 * @email sunyuqian0211@gmail.com
 * @date 2021-08-16
 */
public interface SysUserRoleService extends IService<SysUserRoleEntity> {

    void saveUserRoles(Collection<Long> roleIdList, Long userId);

    void removeByUserIds(Collection<Long> userIds);

    void removeByRoleIds(Collection<Long> roleIds);

    List<Long> listUserIdsByMenuId(Long menuId);
}

