package pers.syq.fastadmin.backstage.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pers.syq.fastadmin.backstage.annotation.SysLog;
import pers.syq.fastadmin.backstage.common.entity.AuthUser;
import pers.syq.fastadmin.backstage.common.utils.PageUtils;
import pers.syq.fastadmin.backstage.common.utils.R;
import pers.syq.fastadmin.backstage.common.utils.Save;
import pers.syq.fastadmin.backstage.common.utils.Update;
import pers.syq.fastadmin.backstage.entity.dto.LoginDTO;
import pers.syq.fastadmin.backstage.entity.dto.UserDTO;
import pers.syq.fastadmin.backstage.entity.SysUserEntity;
import pers.syq.fastadmin.backstage.service.SysUserService;
import pers.syq.fastadmin.backstage.entity.vo.LoginVO;
import pers.syq.fastadmin.backstage.entity.vo.UserInfoVO;
import pers.syq.fastadmin.backstage.entity.vo.UserRoleVO;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;



/**
 *
 * @author syq
 * @email sunyuqian0211@gmail.com
 * @date 2021-08-16
 */
@Slf4j
@RestController
@RequestMapping("sys/user")
public class SysUserController {
    @Autowired
    private SysUserService sysUserService;

    @PreAuthorize("hasAnyAuthority('sys:user:page','ROLE_ADMIN')")
    @GetMapping("/page")
    public R<PageUtils> page(@RequestParam Map<String, Object> params){
        PageUtils page = sysUserService.queryPage(params);
        return R.ok(page);
    }

    @PreAuthorize("hasAnyAuthority('sys:user:get','ROLE_ADMIN')")
    @GetMapping("/{id}")
    public R<SysUserEntity> getById(@PathVariable("id") Long id){
		SysUserEntity sysUser = sysUserService.getById(id);
        return R.ok(sysUser);
    }

    @PreAuthorize("hasAnyAuthority('sys:user:user-role','ROLE_ADMIN')")
    @GetMapping("/user-role/{id}")
    public R<UserRoleVO> getUserRoleVO(@PathVariable("id") Long id){
        UserRoleVO userRoleVO = sysUserService.getUserRoleVO(id);
        return R.ok(userRoleVO);
    }

    @SysLog("Create user")
    @PreAuthorize("hasAnyAuthority('sys:user:save','ROLE_ADMIN')")
    @PostMapping
    public R<?> save(@RequestBody @Validated(Save.class) UserDTO userDTO){
		sysUserService.saveUserDTO(userDTO);
        return R.ok();
    }

    @SysLog("Update user")
    @PreAuthorize("hasAnyAuthority('sys:user:update','ROLE_ADMIN') or #authUser.userId == #userDTO.id")
    @PutMapping
    public R<?> update(@RequestBody @Validated(Update.class) UserDTO userDTO,@AuthenticationPrincipal AuthUser authUser){
		sysUserService.updateUserDTO(userDTO);
        return R.ok();
    }

    @SysLog("Delete Users")
    @PreAuthorize("hasAnyAuthority('sys:user:delete','ROLE_ADMIN')")
    @DeleteMapping("/batch")
    public R<?> deleteBatch(@RequestParam("ids") List<Long> ids){
		sysUserService.removeBatch(ids);
        return R.ok();
    }


    @PostMapping("/login")
    public R<LoginVO> login(@RequestBody @Valid LoginDTO loginDTO){
        String token = sysUserService.login(loginDTO);
        return R.ok(new LoginVO(token));
    }


    @GetMapping("/info")
    public R<UserInfoVO> getUserInfo(@AuthenticationPrincipal AuthUser user){
        UserInfoVO userInfoVO = sysUserService.getUserInfo(user.getUserId());
        return R.ok(userInfoVO);
    }

    @PostMapping("/logout")
    public R<?> logout(@AuthenticationPrincipal AuthUser user){
        if (user != null){
            sysUserService.logout(user.getUserId());
        }
        return R.ok();
    }




}
