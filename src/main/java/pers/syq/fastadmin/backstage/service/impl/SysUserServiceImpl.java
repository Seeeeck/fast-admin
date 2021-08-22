package pers.syq.fastadmin.backstage.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pers.syq.fastadmin.backstage.common.constants.SecurityConstants;
import pers.syq.fastadmin.backstage.common.exception.BaseException;
import pers.syq.fastadmin.backstage.common.exception.ErrorCode;
import pers.syq.fastadmin.backstage.common.utils.JwtTokenUtils;
import pers.syq.fastadmin.backstage.common.utils.PageUtils;
import pers.syq.fastadmin.backstage.common.utils.SecurityUtils;
import pers.syq.fastadmin.backstage.constants.WebConstants;
import pers.syq.fastadmin.backstage.dto.LoginDTO;
import pers.syq.fastadmin.backstage.dto.UserDTO;
import pers.syq.fastadmin.backstage.entity.SysMenuEntity;
import pers.syq.fastadmin.backstage.entity.SysRoleEntity;
import pers.syq.fastadmin.backstage.entity.SysUserEntity;
import pers.syq.fastadmin.backstage.entity.SysUserRoleEntity;
import pers.syq.fastadmin.backstage.mapper.SysUserMapper;
import pers.syq.fastadmin.backstage.service.SysMenuService;
import pers.syq.fastadmin.backstage.service.SysRoleService;
import pers.syq.fastadmin.backstage.service.SysUserRoleService;
import pers.syq.fastadmin.backstage.service.SysUserService;
import pers.syq.fastadmin.backstage.vo.*;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


@Service("sysUserService")
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUserEntity> implements SysUserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private SysMenuService sysMenuService;

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private SysUserRoleService sysUserRoleService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        LambdaQueryWrapper<SysUserEntity> wrapper = new LambdaQueryWrapper<>();
        Object username = params.get("username");
        if (username != null){
            wrapper.like(SysUserEntity::getUsername,username);
        }
        IPage<SysUserEntity> page = this.page(PageUtils.getPage(params),wrapper);
        IPage<UserVO> userVOIPage = page.convert(user -> {
            UserVO userVO = new UserVO();
            BeanUtil.copyProperties(user, userVO);
            return userVO;
        });
        return new PageUtils(userVOIPage);
    }


    @Override
    public String login(LoginDTO loginDTO) {
        String code = (String) redisTemplate.opsForValue().get(loginDTO.getKey());
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
        if (isAdmin(sysUser.getId())){
            permissions.add(SecurityConstants.ADMIN_ROLE_NAME);
        }
        String token = JwtTokenUtils.createToken(sysUser.getUsername(), String.valueOf(sysUser.getId()), permissions);
        redisTemplate.opsForValue().set(SecurityConstants.REDIS_TOKEN_PREFIX + sysUser.getId().toString(),token,SecurityConstants.TOKEN_EXPIRATION, TimeUnit.MINUTES);
        return SecurityConstants.TOKEN_PREFIX + token;
    }

    @Override
    public List<String> listPermissionsByUserId(Long id) {
        List<SysMenuEntity> menus = sysMenuService.listByUserId(id);
        if (CollectionUtil.isEmpty(menus)){
            return new ArrayList<>();
        }
        List<String> permissionList = menus.stream().map(SysMenuEntity::getPerms).collect(Collectors.toList());
        return parsePermissions(permissionList);
    }

    private List<String> parsePermissions(List<String> permissionList){
        List<String> permissions = new ArrayList<>();
        for (String permissionsStr : permissionList) {
            if (StrUtil.isNotBlank(permissionsStr)){
                permissions.addAll(Arrays.asList(permissionsStr.split(",")));
            }
        }
        return permissions;
    }

    @Override
    public UserInfoVO getUserInfo(Long id) {
        UserInfoVO userInfoVO = new UserInfoVO();
        SysUserEntity sysUserEntity = this.getById(id);
        userInfoVO.setUsername(sysUserEntity.getUsername());
        userInfoVO.setAvatar(sysUserEntity.getAvatar());
        List<SysMenuEntity> menus = sysMenuService.listByUserId(sysUserEntity.getId());
        List<String> permissions = parsePermissions(menus.stream().map(SysMenuEntity::getPerms).collect(Collectors.toList()));
        if (isAdmin(sysUserEntity.getId())){
            permissions.add(SecurityConstants.ADMIN_ROLE_NAME);
        }
        HashSet<String> perms = new HashSet<>(permissions);
        userInfoVO.setPermissions(perms);
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

    private boolean isAdmin(Long userId){
        List<SysRoleEntity> roles = sysRoleService.listByUserId(userId);
        for (SysRoleEntity role : roles) {
            if (SecurityConstants.ADMIN_ROLE_NAME.equals(role.getRoleName())){
                return true;
            }
        }
        return false;
    }

    @Override
    public void logout(Long id) {
        SysUserEntity sysUserEntity = this.getById(id);
        redisTemplate.delete(SecurityConstants.REDIS_TOKEN_PREFIX +sysUserEntity.getId().toString());
    }

    @Override
    public UserRoleVO getUserRoleVO(Long id) {
        SysUserEntity sysUserEntity = this.getById(id);
        UserRoleVO userRoleVO = new UserRoleVO();
        BeanUtil.copyProperties(sysUserEntity,userRoleVO);
        List<SysRoleEntity> sysRoleEntities = sysRoleService.listByUserId(id);
        if (!sysRoleEntities.isEmpty()){
            List<Long> roleIdList = sysRoleEntities.stream().map(SysRoleEntity::getId).collect(Collectors.toList());
            userRoleVO.setRoleIdList(roleIdList);
        }
        return userRoleVO;
    }
    @Transactional
    @Override
    public void saveUserDTO(UserDTO userDTO) {
        SysUserEntity existUser = this.getOne(new LambdaQueryWrapper<SysUserEntity>().eq(SysUserEntity::getUsername, userDTO.getUsername()));
        if (existUser != null){
            throw new BaseException(ErrorCode.USERNAME_EXISTS_EXCEPTION);
        }
        SysUserEntity userEntity = new SysUserEntity();
        userEntity.setCreateUserId(SecurityUtils.getUserId().get());
        BeanUtil.copyProperties(userDTO,userEntity);
        userEntity.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        userEntity.setAvatar(WebConstants.DEFAULT_AVATAR);
        this.save(userEntity);
        if (CollectionUtil.isNotEmpty(userDTO.getRoleIdList())){
            sysUserRoleService.saveUserRoles(userDTO.getRoleIdList(),userEntity.getId());
        }
    }

    @Transactional
    @Override
    public void updateUserDTO(UserDTO userDTO) {
        String password = userDTO.getPassword();
        if (password != null && password.trim().equals("")){
            throw new BaseException(ErrorCode.ILLEGAL_PASSWORD_EXCEPTION);
        }
        SysUserEntity userEntity = new SysUserEntity();
        BeanUtil.copyProperties(userDTO,userEntity);
        if (password != null){
            userEntity.setPassword(passwordEncoder.encode(password));
        }
        userEntity.setUsername(null);
        this.updateById(userEntity);
        this.sysUserRoleService.remove(new LambdaQueryWrapper<SysUserRoleEntity>().eq(SysUserRoleEntity::getUserId,userEntity.getId()));
        if (CollectionUtil.isNotEmpty(userDTO.getRoleIdList())){
            sysUserRoleService.saveUserRoles(userDTO.getRoleIdList(),userEntity.getId());
        }
    }
    @Transactional
    @Override
    public void removeBatch(List<Long> ids) {
        this.removeByIds(ids);
        sysUserRoleService.removeByUserIds(ids);
    }


    private List<RouteVO> generateRouteChildren(SysMenuEntity target, List<SysMenuEntity> menus) {
        return menus.stream().filter(menu -> menu.getParentId().equals(target.getId()) && menu.getType() == 1)
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