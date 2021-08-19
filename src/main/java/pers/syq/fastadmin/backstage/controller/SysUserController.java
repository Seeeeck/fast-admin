package pers.syq.fastadmin.backstage.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pers.syq.fastadmin.backstage.common.utils.PageUtils;
import pers.syq.fastadmin.backstage.common.utils.R;
import pers.syq.fastadmin.backstage.dto.LoginDTO;
import pers.syq.fastadmin.backstage.entity.SysUserEntity;
import pers.syq.fastadmin.backstage.service.SysUserService;
import pers.syq.fastadmin.backstage.vo.LoginVO;
import pers.syq.fastadmin.backstage.vo.UserInfoVO;

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

    @GetMapping
    public R<PageUtils> list(@RequestParam Map<String, Object> params){
        PageUtils page = sysUserService.queryPage(params);
        return R.ok(page);
    }

    @GetMapping("/{id}")
    public R<SysUserEntity> getById(@PathVariable("id") Long id){
		SysUserEntity sysUser = sysUserService.getById(id);
        return R.ok(sysUser);
    }

    @PostMapping
    public R<?> save(@RequestBody SysUserEntity sysUser){
		sysUserService.save(sysUser);
        return R.ok();
    }

    @PutMapping
    public R<?> update(@RequestBody SysUserEntity sysUser){
		sysUserService.updateById(sysUser);
        return R.ok();
    }


    @DeleteMapping("/batch")
    public R<?> deleteBatch(@RequestBody List<Long> ids){
		sysUserService.removeByIds(ids);
        return R.ok();
    }

    @DeleteMapping("/{id}")
    public R<?> deleteById(@PathVariable("id") Long id){
        sysUserService.removeById(id);
        return R.ok();
    }

    @PostMapping("/login")
    public R<LoginVO> login(@RequestBody @Valid LoginDTO loginDTO){
        String token = sysUserService.login(loginDTO);
        return R.ok(new LoginVO(token));
    }

    @GetMapping("/info")
    public R<UserInfoVO> getUserInfo(){
        UserInfoVO userInfoVO = sysUserService.getUserInfo();
        return R.ok(userInfoVO);
    }

    @PostMapping("/logout")
    public R<?> logout(){
        sysUserService.logout();
        return R.ok();
    }




}
