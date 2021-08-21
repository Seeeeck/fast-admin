package pers.syq.fastadmin.backstage.dto;

import lombok.Data;
import pers.syq.fastadmin.backstage.common.utils.Save;
import pers.syq.fastadmin.backstage.common.utils.Update;

import javax.validation.constraints.*;
import java.util.List;

@Data
public class UserDTO {
    @NotNull(groups = Update.class)
    private Long id;
    @NotBlank(groups = {Save.class})
    private String username;
    @NotBlank(groups = Save.class)
    @Size(min=5,groups = {Save.class,Update.class})
    private String password;
    @Email(groups = {Save.class,Update.class})
    private String email;
    @Pattern(regexp = "^[0-9]{11}$",groups = {Save.class,Update.class})
    private String mobile;
    @NotNull(groups = {Save.class,Update.class})
    private Boolean enable;
    //@Size(min=1,groups = {Save.class,Update.class})
    private List<Long> roleIdList;
}
