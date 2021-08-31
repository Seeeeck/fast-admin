package pers.syq.fastadmin.backstage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import pers.syq.fastadmin.backstage.common.utils.PageUtils;
import pers.syq.fastadmin.backstage.entity.dto.RoleDTO;
import pers.syq.fastadmin.backstage.entity.SysRoleEntity;
import pers.syq.fastadmin.backstage.entity.vo.RoleMenuVO;

import java.util.List;
import java.util.Map;

/**
 *
 * @author syq
 * @email sunyuqian0211@gmail.com
 * @date 2021-08-16
 */
public interface SysRoleService extends IService<SysRoleEntity> {
    PageUtils queryPage(Map<String, Object> params);

    List<SysRoleEntity> listByUserId(Long id);

    void saveRoleDTO(RoleDTO roleDTO);

    void updateRoleDTO(RoleDTO roleDTO);

    RoleMenuVO getRoleMenuVO(Long id);

    void removeBatch(List<Long> ids);

    int countAdminByUserId(Long userId);
}

