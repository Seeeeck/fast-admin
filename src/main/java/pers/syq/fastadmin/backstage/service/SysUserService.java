package pers.syq.fastadmin.backstage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import pers.syq.fastadmin.backstage.dto.LoginDTO;
import pers.syq.fastadmin.backstage.dto.UserDTO;
import pers.syq.fastadmin.backstage.entity.SysUserEntity;
import pers.syq.fastadmin.backstage.common.utils.PageUtils;
import pers.syq.fastadmin.backstage.vo.UserInfoVO;
import pers.syq.fastadmin.backstage.vo.UserRoleVO;

import java.util.List;
import java.util.Map;

/**
 *
 * @author syq
 * @email sunyuqian0211@gmail.com
 * @date 2021-08-16
 */
public interface SysUserService extends IService<SysUserEntity> {
    PageUtils queryPage(Map<String, Object> params);

    String login(LoginDTO loginDTO);

    List<String> listPermissionsByUserId(Long id);

    UserInfoVO getUserInfo(Long userId);

    void logout(Long id);

    UserRoleVO getUserRoleVO(Long id);

    void saveUserDTO(UserDTO userDTO);

    void updateUserDTO(UserDTO userDTO);

    void removeBatch(List<Long> ids);

    //String getCurrentUserName();
}

