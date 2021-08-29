package pers.syq.fastadmin.backstage.entity.vo;

import lombok.Data;

import java.util.List;

@Data
public class RoleMenuVO {
    private Long id;
    private String remark;
    private String roleName;
    private List<Long> menuIdList;
}
