package pers.syq.fastadmin.backstage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import pers.syq.fastadmin.backstage.common.utils.PageUtils;
import pers.syq.fastadmin.backstage.entity.SysRoleEntity;

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
}

