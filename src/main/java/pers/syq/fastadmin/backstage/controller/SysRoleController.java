package pers.syq.fastadmin.backstage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pers.syq.fastadmin.backstage.common.utils.PageUtils;
import pers.syq.fastadmin.backstage.common.utils.R;
import pers.syq.fastadmin.backstage.common.utils.Save;
import pers.syq.fastadmin.backstage.common.utils.Update;
import pers.syq.fastadmin.backstage.dto.RoleDTO;
import pers.syq.fastadmin.backstage.entity.SysRoleEntity;
import pers.syq.fastadmin.backstage.service.SysRoleService;
import pers.syq.fastadmin.backstage.vo.RoleMenuVO;

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

    @GetMapping("/page")
    public R<PageUtils> page(@RequestParam Map<String, Object> params){
        PageUtils page = sysRoleService.queryPage(params);
        return R.ok(page);
    }

    @GetMapping
    public R<List<SysRoleEntity>> list(){
        return R.ok(sysRoleService.list());
    }

    @GetMapping("/{id}")
    public R<SysRoleEntity> getById(@PathVariable("id") Long id){
		SysRoleEntity sysRole = sysRoleService.getById(id);
        return R.ok(sysRole);
    }

    @PostMapping
    public R<?> save(@RequestBody @Validated(Save.class) RoleDTO roleDTO){
		sysRoleService.saveRoleDTO(roleDTO);
        return R.ok();
    }

    @PutMapping
    public R<?> update(@RequestBody @Validated(Update.class) RoleDTO roleDTO){
		sysRoleService.updateRoleDTO(roleDTO);
        return R.ok();
    }

    @DeleteMapping("/batch")
    public R<?> deleteBatch(@RequestParam("ids") List<Long> ids){
		sysRoleService.removeBatch(ids);
        return R.ok();
    }

    @GetMapping("/role_menu/{id}")
    public R<RoleMenuVO> getRoleMenuVO(@PathVariable("id") Long id){
        RoleMenuVO roleMenuVO = sysRoleService.getRoleMenuVO(id);
        return R.ok(roleMenuVO);
    }


}
