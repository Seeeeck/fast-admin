package pers.syq.fastadmin.backstage.entity.vo;

import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class UserInfoVO {
    private Long id;
    private String username;
    private String avatar;
    private List<RouteVO> menus;
    private Set<String> permissions;
}
