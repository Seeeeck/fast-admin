package pers.syq.fastadmin.backstage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
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

    @PreAuthorize("hasAuthority('sys:menu:list')")
    @GetMapping
    public R<PageUtils> list(@RequestParam Map<String, Object> params){
        PageUtils page = sysMenuService.queryPage(params);
        return R.ok(page);
    }

    @PreAuthorize("hasAuthority('sys:menu:get')")
    @GetMapping("/{id}")
    public R<SysMenuEntity> getById(@PathVariable("id") Long id){
		SysMenuEntity sysMenu = sysMenuService.getById(id);
        return R.ok(sysMenu);
    }

    @PreAuthorize("hasAuthority('sys:menu:save')")
    @PostMapping
    public R<?> save(@RequestBody SysMenuEntity sysMenu){
		sysMenuService.save(sysMenu);
        return R.ok();
    }

    @PreAuthorize("hasAuthority('sys:menu:update')")
    @PutMapping
    public R<?> update(@RequestBody SysMenuEntity sysMenu){
		sysMenuService.updateById(sysMenu);
        return R.ok();
    }


    @PreAuthorize("hasAuthority('sys:menu:delete')")
    @DeleteMapping("/batch")
    public R<?> deleteBatch(@RequestParam("ids") List<Long> ids){
		sysMenuService.removeByIds(ids);
        return R.ok();
    }

}
