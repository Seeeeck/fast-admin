package pers.syq.fastadmin.backstage.controller;

import cn.hutool.core.lang.tree.Tree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pers.syq.fastadmin.backstage.annotation.SysLog;
import pers.syq.fastadmin.backstage.common.utils.R;
import pers.syq.fastadmin.backstage.entity.SysMenuEntity;
import pers.syq.fastadmin.backstage.service.SysMenuService;

import javax.validation.constraints.NotBlank;
import java.util.List;



/**
 *
 * @author syq
 * @email sunyuqian0211@gmail.com
 * @date 2021-08-16
 */
@Validated
@RestController
@RequestMapping("sys/menu")
public class SysMenuController {
    @Autowired
    private SysMenuService sysMenuService;

    @PreAuthorize("hasAnyAuthority('sys:menu:tree','ROLE_ADMIN')")
    @GetMapping("/tree")
    public R<?> listMenusForTree(@RequestParam("op") @NotBlank String option,@RequestParam(value = "noButtonType",required = false) Boolean noButtonType){
        List<Tree<Long>> tree = sysMenuService.listMenusForTree(option,noButtonType);
        return R.ok(tree);
    }

    @PreAuthorize("hasAnyAuthority('sys:menu:get','ROLE_ADMIN')")
    @GetMapping("/{id}")
    public R<SysMenuEntity> getById(@PathVariable("id") Long id){
		SysMenuEntity sysMenu = sysMenuService.getById(id);
        return R.ok(sysMenu);
    }

    @SysLog("Create menu")
    @PreAuthorize("hasAnyAuthority('sys:menu:save','ROLE_ADMIN')")
    @PostMapping
    public R<?> save(@RequestBody SysMenuEntity sysMenu){
		sysMenuService.save(sysMenu);
        return R.ok();
    }

    @SysLog("Update menu")
    @PreAuthorize("hasAnyAuthority('sys:menu:update','ROLE_ADMIN')")
    @PutMapping
    public R<?> update(@RequestBody SysMenuEntity sysMenu){
		sysMenuService.updateById(sysMenu);
        return R.ok();
    }

    @SysLog("Delete menu")
    @PreAuthorize("hasAnyAuthority('sys:menu:delete','ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public R<?> delete(@PathVariable("id") Long id){
        sysMenuService.removeById(id);
        return R.ok();
    }


    @PreAuthorize("hasAnyAuthority('sys:menu:get','ROLE_ADMIN')")
    @GetMapping("/parent")
    public R<SysMenuEntity> getParentMenuById(@RequestParam("id") Long id){
        SysMenuEntity menu = sysMenuService.getParentById(id);
        return R.ok(menu);
    }


}
