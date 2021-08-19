package pers.syq.fastadmin.backstage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pers.syq.fastadmin.backstage.entity.SysRoleEntity;
import pers.syq.fastadmin.backstage.service.SysRoleService;
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
@RequestMapping("sys/role")
public class SysRoleController {
    @Autowired
    private SysRoleService sysRoleService;

    @GetMapping
    public R<PageUtils> list(@RequestParam Map<String, Object> params){
        PageUtils page = sysRoleService.queryPage(params);
        return R.ok(page);
    }

    @GetMapping("/{id}")
    public R<SysRoleEntity> getById(@PathVariable("id") Long id){
		SysRoleEntity sysRole = sysRoleService.getById(id);
        return R.ok(sysRole);
    }

    @PostMapping
    public R<?> save(@RequestBody SysRoleEntity sysRole){
		sysRoleService.save(sysRole);
        return R.ok();
    }

    @PutMapping
    public R<?> update(@RequestBody SysRoleEntity sysRole){
		sysRoleService.updateById(sysRole);
        return R.ok();
    }


    @DeleteMapping("/batch")
    public R<?> deleteBatch(@RequestBody List<Long> ids){
		sysRoleService.removeByIds(ids);
        return R.ok();
    }

    @DeleteMapping("/{id}")
    public R<?> deleteById(@PathVariable("id") Long id){
        sysRoleService.removeById(id);
        return R.ok();
    }
}
