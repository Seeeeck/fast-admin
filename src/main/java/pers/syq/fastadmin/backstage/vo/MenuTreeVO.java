package pers.syq.fastadmin.backstage.vo;

import lombok.Data;

import java.util.List;

@Data
public class MenuTreeVO {
    private Long id;
    private Long parentId;
    private String name;
    private Integer type;
    private List<MenuTreeVO> children;
}
