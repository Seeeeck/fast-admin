package pers.syq.fastadmin.backstage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import pers.syq.fastadmin.backstage.entity.SysUserRoleEntity;

import java.util.List;

/**
 *
 * @author syq
 * @email sunyuqian0211@gmail.com
 * @date 2021-08-16
 */
public interface SysUserRoleService extends IService<SysUserRoleEntity> {

    void saveUserRoles(List<Long> roleIdList, Long userId);

    void removeByUserIds(List<Long> userIds);

    void removeByRoleIds(List<Long> roleIds);
}

