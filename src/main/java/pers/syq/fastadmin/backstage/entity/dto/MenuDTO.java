package pers.syq.fastadmin.backstage.entity.dto;

import lombok.Data;
import pers.syq.fastadmin.backstage.common.utils.Save;
import pers.syq.fastadmin.backstage.common.utils.Update;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class MenuDTO {
    @NotNull(groups = Update.class)
    private Long id;

    @NotNull(groups = {Update.class, Save.class})
    private Long parentId;

    @NotBlank(groups = {Update.class, Save.class})
    private String name;


    private String path;


    private String perms;

    @NotNull(groups = {Update.class, Save.class})
    private Integer type;


    private String icon;

    @NotNull(groups = {Update.class, Save.class})
    private Integer orderNum;

    @NotNull(groups = {Update.class, Save.class})
    private Boolean hidden;
}
