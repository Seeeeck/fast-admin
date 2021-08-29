package pers.syq.fastadmin.backstage.entity.dto;

import lombok.Data;
import pers.syq.fastadmin.backstage.common.utils.Save;
import pers.syq.fastadmin.backstage.common.utils.Update;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ScheduleJobDTO {

    @NotNull(groups = {Update.class})
    private Long id;

    @NotBlank(groups = {Update.class, Save.class})
    private String beanName;

    private String params;

    @NotBlank(groups = {Update.class, Save.class})
    private String cronExpression;

    private String remark;

}
