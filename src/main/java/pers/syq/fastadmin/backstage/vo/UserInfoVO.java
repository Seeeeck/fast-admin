package pers.syq.fastadmin.backstage.vo;

import lombok.Data;

import java.util.List;

@Data
public class UserInfoVO {
    private String username;
    private String avatar;
    private List<RouteVO> menus;
}
