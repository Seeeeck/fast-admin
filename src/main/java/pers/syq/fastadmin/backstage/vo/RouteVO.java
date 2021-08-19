package pers.syq.fastadmin.backstage.vo;

import lombok.Data;

import java.util.List;
@Data
public class RouteVO {
    private Long id;
    private String path;
    private String component;
    private String name;
    private RouteMetaVO meta;
    private Integer type;
    private Boolean hidden;
    private List<RouteVO> children;
}
