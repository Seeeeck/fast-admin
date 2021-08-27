package pers.syq.fastadmin.backstage.dto;

import lombok.Data;
import pers.syq.fastadmin.backstage.common.utils.Save;
import pers.syq.fastadmin.backstage.common.utils.Update;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
public class RoleDTO {
    @NotNull(groups = Update.class)
    private Long id;
    @NotBlank(groups = {Save.class,Update.class})
    private String roleName;

    private String remark;

    private Set<Long> menuIds;
}
