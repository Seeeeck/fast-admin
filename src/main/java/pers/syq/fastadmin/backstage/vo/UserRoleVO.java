package pers.syq.fastadmin.backstage.vo;

import lombok.Data;

import java.util.List;

@Data
public class UserRoleVO {
    private Long id;

    /**
     * 用户名
     */
    private String username;


    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机号
     */
    private String mobile;

    private String avatar;

    /**
     * 状态  0：禁用   1：正常
     */
    private Boolean enable;

    private List<Long> roleIdList;

}
