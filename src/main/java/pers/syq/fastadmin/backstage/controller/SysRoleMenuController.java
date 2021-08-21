package pers.syq.fastadmin.backstage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pers.syq.fastadmin.backstage.common.utils.PageUtils;
import pers.syq.fastadmin.backstage.common.utils.R;
import pers.syq.fastadmin.backstage.entity.SysRoleMenuEntity;
import pers.syq.fastadmin.backstage.service.SysRoleMenuService;

import java.util.List;
import java.util.Map;



/**
 *
 * @author syq
 * @email sunyuqian0211@gmail.com
 * @date 2021-08-16
 */
@RestController
@RequestMapping("sys/rolemenu")
public class SysRoleMenuController {
    @Autowired
    private SysRoleMenuService sysRoleMenuService;

    @GetMapping("/page")
    public R<PageUtils> page(@RequestParam Map<String, Object> params){
        PageUtils page = sysRoleMenuService.queryPage(params);
        return R.ok(page);
    }

    @GetMapping("/{id}")
    public R<SysRoleMenuEntity> getById(@PathVariable("id") Long id){
		SysRoleMenuEntity sysRoleMenu = sysRoleMenuService.getById(id);
        return R.ok(sysRoleMenu);
    }

    @PostMapping
    public R<?> save(@RequestBody SysRoleMenuEntity sysRoleMenu){
		sysRoleMenuService.save(sysRoleMenu);
        return R.ok();
    }

    @PutMapping
    public R<?> update(@RequestBody SysRoleMenuEntity sysRoleMenu){
		sysRoleMenuService.updateById(sysRoleMenu);
        return R.ok();
    }


    @DeleteMapping("/batch")
    public R<?> deleteBatch(@RequestParam("ids") List<Long> ids){
		sysRoleMenuService.removeByIds(ids);
        return R.ok();
    }

}
