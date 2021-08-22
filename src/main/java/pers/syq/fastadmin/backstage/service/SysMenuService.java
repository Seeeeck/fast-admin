package pers.syq.fastadmin.backstage.service;

import cn.hutool.core.lang.tree.Tree;
import com.baomidou.mybatisplus.extension.service.IService;
import pers.syq.fastadmin.backstage.common.utils.PageUtils;
import pers.syq.fastadmin.backstage.entity.SysMenuEntity;

import java.util.List;
import java.util.Map;

/**
 *
 * @author syq
 * @email sunyuqian0211@gmail.com
 * @date 2021-08-16
 */
public interface SysMenuService extends IService<SysMenuEntity> {
    PageUtils queryPage(Map<String, Object> params);

    List<SysMenuEntity> listByUserId(Long userId);

    List<Tree<Long>> listMenusForTree();

    List<SysMenuEntity> listByRoleId(Long roleId);

    void removeBatch(List<Long> ids);
}

