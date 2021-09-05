package pers.syq.fastadmin.backstage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import pers.syq.fastadmin.backstage.common.utils.PageUtils;
import pers.syq.fastadmin.backstage.entity.dto.LoginDTO;
import pers.syq.fastadmin.backstage.entity.dto.UserDTO;
import pers.syq.fastadmin.backstage.entity.SysUserEntity;
import pers.syq.fastadmin.backstage.entity.dto.UserOwnDTO;
import pers.syq.fastadmin.backstage.entity.vo.UserInfoVO;
import pers.syq.fastadmin.backstage.entity.vo.UserRoleVO;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author syq
 * @email sunyuqian0211@gmail.com
 * @date 2021-08-16
 */
public interface SysUserService extends IService<SysUserEntity> {
    PageUtils queryPage(Map<String, Object> params);

    String login(LoginDTO loginDTO);

    Set<String> listPermissionsByUserId(Long id);

    UserInfoVO getUserInfo(Long userId);

    void logout(Long id);

    UserRoleVO getUserRoleVO(Long id);

    void saveUserDTO(UserDTO userDTO);

    void updateUserDTO(UserDTO userDTO);

    void removeBatch(List<Long> ids);

    SysUserEntity getByUsername(String username);

    boolean isLegalCaptchaCode(String redisCodeKey, String code);

    void updateUserOwnDTO(UserOwnDTO userOwnDTO);
}

