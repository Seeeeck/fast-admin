package pers.syq.fastadmin.backstage.entity.dto;

import com.github.houbb.sensitive.annotation.Sensitive;
import com.github.houbb.sensitive.core.api.strategory.StrategyEmail;
import com.github.houbb.sensitive.core.api.strategory.StrategyPhone;
import lombok.Data;
import pers.syq.fastadmin.backstage.annotation.sensitive.FastAdminSensitivePasswordStrategy;
import pers.syq.fastadmin.backstage.annotation.sensitive.SensitiveClass;
import pers.syq.fastadmin.backstage.common.utils.Save;
import pers.syq.fastadmin.backstage.common.utils.Update;

import javax.validation.constraints.*;
import java.util.Set;

@Data
@SensitiveClass
public class UserDTO {
    @NotNull(groups = {Update.class})
    private Long id;
    @NotBlank(groups = {Save.class})
    private String username;
    @FastAdminSensitivePasswordStrategy
    @NotBlank(groups = Save.class)
    @Size(min=5,groups = {Save.class,Update.class})
    private String password;
    @Sensitive(strategy = StrategyEmail.class)
    @Email(groups = {Save.class,Update.class})
    private String email;
    @Sensitive(strategy = StrategyPhone.class)
    @Pattern(regexp = "^[0-9]{11}$",groups = {Save.class,Update.class})
    private String mobile;
    private String avatar;
    @NotNull(groups = {Save.class,Update.class})
    private Boolean enable;
    private Set<Long> roleIds;
}
