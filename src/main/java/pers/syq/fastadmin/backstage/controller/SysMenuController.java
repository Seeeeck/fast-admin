package pers.syq.fastadmin.backstage.controller;

import cn.hutool.core.lang.tree.Tree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import pers.syq.fastadmin.backstage.common.entity.AuthUser;
import pers.syq.fastadmin.backstage.common.utils.PageUtils;
import pers.syq.fastadmin.backstage.common.utils.R;
import pers.syq.fastadmin.backstage.entity.SysMenuEntity;
import pers.syq.fastadmin.backstage.service.SysMenuService;

import java.util.List;
import java.util.Map;



/**
 *
 * @author syq
 * @email sunyuqian0211@gmail.com
 * @date 2021-08-16
 */
@RestController
@RequestMapping("sys/menu")
public class SysMenuController {
    @Autowired
    private SysMenuService sysMenuService;

    @PreAuthorize("hasAnyAuthority('sys:menu:page','ROLE_ADMIN')")
    @GetMapping("/page")
    public R<PageUtils> page(@RequestParam Map<String, Object> params){
        PageUtils page = sysMenuService.queryPage(params);
        return R.ok(page);
    }

    @PreAuthorize("hasAnyAuthority('sys:menu:get','ROLE_ADMIN')")
    @GetMapping("/{id}")
    public R<SysMenuEntity> getById(@PathVariable("id") Long id){
		SysMenuEntity sysMenu = sysMenuService.getById(id);
        return R.ok(sysMenu);
    }

    @PreAuthorize("hasAnyAuthority('sys:menu:save','ROLE_ADMIN')")
    @PostMapping
    public R<?> save(@RequestBody SysMenuEntity sysMenu, @AuthenticationPrincipal AuthUser user){
		sysMenuService.save(sysMenu);
        return R.ok();
    }

    @PreAuthorize("hasAnyAuthority('sys:menu:update','ROLE_ADMIN')")
    @PutMapping
    public R<?> update(@RequestBody SysMenuEntity sysMenu){
		sysMenuService.updateById(sysMenu);
        return R.ok();
    }


    @PreAuthorize("hasAnyAuthority('sys:menu:delete','ROLE_ADMIN')")
    @DeleteMapping("/batch")
    public R<?> deleteBatch(@RequestParam("ids") List<Long> ids){
		sysMenuService.removeBatch(ids);
        return R.ok();
    }

    @GetMapping("/tree")
    public R<?> listMenusForTree(){
        List<Tree<Long>> tree = sysMenuService.listMenusForTree();
        return R.ok(tree);
    }

}
