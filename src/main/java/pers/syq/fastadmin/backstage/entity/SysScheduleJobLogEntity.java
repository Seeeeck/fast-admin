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
@TableName("sys_schedule_job_log")
@ApiModel(value="SysScheduleJobLog", description="Schedule job log")
public class SysScheduleJobLogEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty("ID")
	@TableId(value = "id", type = IdType.AUTO)
	private Long id;

	@ApiModelProperty("Job id")
	private Long jobId;

	@ApiModelProperty("Bean name")
	private String beanName;

	@ApiModelProperty("params")
	private String params;

	@ApiModelProperty("Status")
	private Integer status;

	@ApiModelProperty("Error message")
	private String error;

	@ApiModelProperty("Time(ms)")
	private Integer time;

	@ApiModelProperty("Create time")
	@TableField(fill = FieldFill.INSERT)
	private Date createTime;



}
