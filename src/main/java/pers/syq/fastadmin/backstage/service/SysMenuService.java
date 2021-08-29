package pers.syq.fastadmin.backstage.service;

import cn.hutool.core.lang.tree.Tree;
import com.baomidou.mybatisplus.extension.service.IService;
import pers.syq.fastadmin.backstage.entity.dto.MenuDTO;
import pers.syq.fastadmin.backstage.entity.SysMenuEntity;

import java.util.List;

/**
 *
 * @author syq
 * @email sunyuqian0211@gmail.com
 * @date 2021-08-16
 */
public interface SysMenuService extends IService<SysMenuEntity> {

    List<SysMenuEntity> listByUserId(Long userId);

    List<Tree<Long>> listMenusForTree(String option,Boolean noButtonType);

    List<SysMenuEntity> listByRoleId(Long roleId);

    SysMenuEntity getParentById(Long id);

    void saveDTO(MenuDTO menuDTO);

    void updateDTO(MenuDTO menuDTO);
}

