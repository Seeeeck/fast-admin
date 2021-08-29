package pers.syq.fastadmin.backstage.entity.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import pers.syq.fastadmin.backstage.filter.HiddenFilter;

import java.util.List;
@Data
public class RouteVO {
    private Long id;
    private String path;
    private String component;
    private String name;
    private RouteMetaVO meta;
    private Integer type;
    @JsonInclude(value = JsonInclude.Include.CUSTOM,valueFilter = HiddenFilter.class)
    private Boolean hidden;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<RouteVO> children;
}
