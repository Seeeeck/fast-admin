package pers.syq.fastadmin.backstage.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNode;
import cn.hutool.core.lang.tree.TreeNodeConfig;
import cn.hutool.core.lang.tree.TreeUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pers.syq.fastadmin.backstage.common.utils.PageUtils;
import pers.syq.fastadmin.backstage.entity.SysMenuEntity;
import pers.syq.fastadmin.backstage.mapper.SysMenuMapper;
import pers.syq.fastadmin.backstage.service.SysMenuService;
import pers.syq.fastadmin.backstage.service.SysRoleMenuService;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service("sysMenuService")
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenuEntity> implements SysMenuService {
    @Autowired
    private SysRoleMenuService roleMenuService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SysMenuEntity> page = this.page(PageUtils.getPage(params));
        return new PageUtils(page);
    }

    @Override
    public List<SysMenuEntity> listByUserId(Long userId) {
        return this.baseMapper.selectListByUserId(userId);
    }

    @Override
    public List<Tree<Long>> listMenusForTree() {
        List<SysMenuEntity> menuEntities = this.list();
        if (CollectionUtil.isNotEmpty(menuEntities)){
            List<TreeNode<Long>> treeNodes = menuEntities.stream()
                    .map(menu -> new TreeNode<>(menu.getId(), menu.getParentId(), menu.getName(), menu.getOrderNum()))
                    .collect(Collectors.toList());
            TreeNodeConfig treeNodeConfig = new TreeNodeConfig();
            treeNodeConfig.setWeightKey("orderNum");
            return TreeUtil.build(treeNodes,0L,treeNodeConfig, BeanUtil::copyProperties);
        }
        return null;
    }

    @Override
    public List<SysMenuEntity> listByRoleId(Long roleId) {
        return this.baseMapper.selectListByRoleId(roleId);
    }

    @Transactional
    @Override
    public void removeBatch(List<Long> ids) {
        this.removeByIds(ids);
        roleMenuService.removeByMenuIds(ids);
    }


}