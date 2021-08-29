package pers.syq.fastadmin.backstage.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;



/**
 *
 * @author syq
 * @email sunyuqian0211@gmail.com
 * @date 2021-08-29
 */
@Data
@TableName("sys_schedule_job")
@ApiModel(value="SysScheduleJob", description="Schedule job")
public class SysScheduleJobEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final String JOB_PARAM_KEY = "JOB_KEY";

	@ApiModelProperty("ID")
	@TableId(value = "id", type = IdType.AUTO)
	private Long id;

	@ApiModelProperty("Bean name")
	private String beanName;

	@ApiModelProperty("Params")
	private String params;

	@ApiModelProperty("Cron expression")
	private String cronExpression;

	@ApiModelProperty("Status")
	private Integer status;

	@ApiModelProperty("Remark")
	private String remark;

	@ApiModelProperty("Create time")
	@TableField(fill = FieldFill.INSERT)
	private Date createTime;



}
