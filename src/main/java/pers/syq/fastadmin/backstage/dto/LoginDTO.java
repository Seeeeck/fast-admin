package pers.syq.fastadmin.backstage.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginDTO {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @NotBlank
    private String key;
    @NotBlank
    private String code;
}
