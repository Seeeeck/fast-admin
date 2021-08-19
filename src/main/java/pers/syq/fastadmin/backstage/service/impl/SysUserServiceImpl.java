package pers.syq.fastadmin.backstage.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pers.syq.fastadmin.backstage.common.constants.SecurityConstants;
import pers.syq.fastadmin.backstage.common.exception.BaseException;
import pers.syq.fastadmin.backstage.common.exception.ErrorCode;
import pers.syq.fastadmin.backstage.common.utils.JwtTokenUtils;
import pers.syq.fastadmin.backstage.common.utils.PageUtils;
import pers.syq.fastadmin.backstage.dto.LoginDTO;
import pers.syq.fastadmin.backstage.entity.SysMenuEntity;
import pers.syq.fastadmin.backstage.entity.SysUserEntity;
import pers.syq.fastadmin.backstage.mapper.SysUserMapper;
import pers.syq.fastadmin.backstage.service.SysMenuService;
import pers.syq.fastadmin.backstage.service.SysUserService;
import pers.syq.fastadmin.backstage.vo.RouteMetaVO;
import pers.syq.fastadmin.backstage.vo.RouteVO;
import pers.syq.fastadmin.backstage.vo.UserInfoVO;

import java.util.*;
import java.util.stream.Collectors;


@Service("sysUserService")
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUserEntity> implements SysUserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private SysMenuService sysMenuService;

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SysUserEntity> page = this.page(getPage(params), new QueryWrapper<SysUserEntity>());
        return new PageUtils(page);
    }

    private IPage<SysUserEntity> getPage(Map<String, Object> params){
        long curPage = 1;
        long size = 10;
        if(params.get("page") != null){
            curPage = Long.parseLong((String)params.get("page"));
        }
        if(params.get("size") != null){
            size = Long.parseLong((String)params.get("size"));
        }
        return new Page<>(curPage, size);
    }

    @Override
    public String login(LoginDTO loginDTO) {
        String code = (String)redisTemplate.opsForValue().get(loginDTO.getKey());
        if (StrUtil.isBlank(code) || !loginDTO.getCode().equalsIgnoreCase(code)){
            throw new BaseException(ErrorCode.CAPTCHA_VALID_EXCEPTION);
        }
        SysUserEntity sysUser = this.getOne(new LambdaQueryWrapper<SysUserEntity>().eq(SysUserEntity::getUsername, loginDTO.getUsername()));
        if (sysUser == null || !passwordEncoder.matches(loginDTO.getPassword(),sysUser.getPassword())){
            throw new BadCredentialsException("The user name or password is not correct.");
        }
        if (!sysUser.getEnable()){
            throw new BadCredentialsException("User is forbidden to login");
        }
        List<String> permissions = listPermissionsByUserId(sysUser.getId());
        String token = JwtTokenUtils.createToken(sysUser.getUsername(), String.valueOf(sysUser.getId()), permissions);
        redisTemplate.opsForValue().set(SecurityConstants.REDIS_TOKEN_PREFIX + sysUser.getId().toString(),token, SecurityConstants.EXPIRATION);
        return token;
    }

    @Override
    public List<String> listPermissionsByUserId(Long id) {
        List<String> permissionList = sysMenuService.listByUserId(id).stream().map(SysMenuEntity::getPerms).collect(Collectors.toList());
        List<String> permissions = new ArrayList<>();
        for (String permissionsStr : permissionList) {
            if (StrUtil.isNotBlank(permissionsStr)){
                permissions.addAll(Arrays.asList(permissionsStr.split(",")));
            }
        }
        return permissions;
    }

    @Override
    public UserInfoVO getUserInfo() {
        UserInfoVO userInfoVO = new UserInfoVO();
        SysUserEntity sysUserEntity = this.getOne(new LambdaQueryWrapper<SysUserEntity>().eq(SysUserEntity::getUsername, this.getCurrentUserName()));
        userInfoVO.setUsername(sysUserEntity.getUsername());
        userInfoVO.setAvatar(sysUserEntity.getAvatar());
        List<SysMenuEntity> menus = sysMenuService.listByUserId(sysUserEntity.getId());
        List<RouteVO> routeVOList = menus.stream().filter(menu -> menu.getParentId() == 0)
                .sorted(Comparator.comparingInt(SysMenuEntity::getOrderNum))
                .map(menu -> {
            RouteVO routeVO = generateRouteVO(menu);
            routeVO.setChildren(generateRouteChildren(menu,menus));
            return routeVO;
        }).collect(Collectors.toList());
        userInfoVO.setMenus(routeVOList);
        return userInfoVO;
    }

    @Override
    public void logout() {
        String currentUserName = this.getCurrentUserName();
        if (currentUserName != null){
            SysUserEntity sysUserEntity = this.getOne(new LambdaQueryWrapper<SysUserEntity>().eq(SysUserEntity::getUsername, currentUserName));
            redisTemplate.delete(SecurityConstants.REDIS_TOKEN_PREFIX +sysUserEntity.getId().toString());
        }

    }

    @Override
    public String getCurrentUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() != null) {
            return (String) authentication.getPrincipal();
        }
        return null;
    }

    private List<RouteVO> generateRouteChildren(SysMenuEntity target, List<SysMenuEntity> menus) {
        return menus.stream().filter(menu -> menu.getParentId().equals(target.getParentId()) && menu.getType() == 1)
                .sorted(Comparator.comparingInt(SysMenuEntity::getOrderNum))
                .map(menu -> {
                    RouteVO routeVO = generateRouteVO(menu);
                    routeVO.setChildren(generateRouteChildren(menu,menus));
                    return routeVO;
                }).collect(Collectors.toList());
    }

    private RouteVO generateRouteVO(SysMenuEntity menu) {
        RouteVO routeVO = new RouteVO();
        BeanUtil.copyProperties(menu, routeVO);
        String componentPath;
        if (menu.getParentId() == 0){
            componentPath = "Layout";
        }else {
            if (menu.getPath().endsWith("/")) {
                componentPath = menu.getPath() + "index";
            } else {
                componentPath = menu.getPath() + "/index";
            }
        }
        routeVO.setComponent(componentPath);
        RouteMetaVO routeMetaVO = new RouteMetaVO();
        routeMetaVO.setTitle(menu.getName());
        routeMetaVO.setIcon(menu.getIcon());
        routeVO.setMeta(routeMetaVO);
        return routeVO;
    }




}