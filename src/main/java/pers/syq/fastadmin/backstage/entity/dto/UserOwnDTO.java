package pers.syq.fastadmin.backstage.entity.dto;

import com.github.houbb.sensitive.annotation.Sensitive;
import com.github.houbb.sensitive.core.api.strategory.StrategyEmail;
import com.github.houbb.sensitive.core.api.strategory.StrategyPhone;
import lombok.Data;
import pers.syq.fastadmin.backstage.annotation.sensitive.FastAdminSensitivePasswordStrategy;
import pers.syq.fastadmin.backstage.annotation.sensitive.SensitiveClass;
import pers.syq.fastadmin.backstage.common.utils.Update;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@SensitiveClass
public class UserOwnDTO {

    @NotNull(groups = {Update.class})
    private Long id;
    @FastAdminSensitivePasswordStrategy
    @Size(min=5,groups = {Update.class})
    private String password;
    @Sensitive(strategy = StrategyEmail.class)
    @Email(groups = {Update.class})
    private String email;
    @Sensitive(strategy = StrategyPhone.class)
    @Pattern(regexp = "^[0-9]{11}$",groups = {Update.class})
    private String mobile;
    @NotNull(groups = {Update.class})
    private String avatar;
}
