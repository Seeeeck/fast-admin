package pers.syq.fastadmin.backstage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pers.syq.fastadmin.backstage.entity.SysUserRoleEntity;
import pers.syq.fastadmin.backstage.service.SysUserRoleService;
import pers.syq.fastadmin.backstage.common.utils.PageUtils;
import pers.syq.fastadmin.backstage.common.utils.R;

import java.util.List;
import java.util.Map;



/**
 *
 * @author syq
 * @email sunyuqian0211@gmail.com
 * @date 2021-08-16
 */
@RestController
@RequestMapping("sys/userrole")
public class SysUserRoleController {
    @Autowired
    private SysUserRoleService sysUserRoleService;

    @GetMapping
    public R<PageUtils> list(@RequestParam Map<String, Object> params){
        PageUtils page = sysUserRoleService.queryPage(params);
        return R.ok(page);
    }

    @GetMapping("/{id}")
    public R<SysUserRoleEntity> getById(@PathVariable("id") Long id){
		SysUserRoleEntity sysUserRole = sysUserRoleService.getById(id);
        return R.ok(sysUserRole);
    }

    @PostMapping
    public R<?> save(@RequestBody SysUserRoleEntity sysUserRole){
		sysUserRoleService.save(sysUserRole);
        return R.ok();
    }

    @PutMapping
    public R<?> update(@RequestBody SysUserRoleEntity sysUserRole){
		sysUserRoleService.updateById(sysUserRole);
        return R.ok();
    }


    @DeleteMapping("/batch")
    public R<?> deleteBatch(@RequestBody List<Long> ids){
		sysUserRoleService.removeByIds(ids);
        return R.ok();
    }

    @DeleteMapping("/{id}")
    public R<?> deleteById(@PathVariable("id") Long id){
        sysUserRoleService.removeById(id);
        return R.ok();
    }
}
