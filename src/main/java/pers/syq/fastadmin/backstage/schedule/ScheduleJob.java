package pers.syq.fastadmin.backstage.schedule;

import cn.hutool.extra.spring.SpringUtil;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import pers.syq.fastadmin.backstage.entity.SysScheduleJobEntity;
import pers.syq.fastadmin.backstage.entity.SysScheduleJobLogEntity;
import pers.syq.fastadmin.backstage.schedule.task.ITask;
import pers.syq.fastadmin.backstage.service.SysScheduleJobLogService;

public class ScheduleJob extends QuartzJobBean {
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        SysScheduleJobEntity jobEntity = (SysScheduleJobEntity) jobExecutionContext.getMergedJobDataMap()
                .get(SysScheduleJobEntity.JOB_PARAM_KEY);
        SysScheduleJobLogService logService = SpringUtil.getBean(SysScheduleJobLogService.class);
        SysScheduleJobLogEntity logEntity = new SysScheduleJobLogEntity();
        logEntity.setJobId(jobEntity.getId());
        logEntity.setBeanName(jobEntity.getBeanName());
        logEntity.setParams(jobEntity.getParams());
        long startTime = System.currentTimeMillis();
        try {
            ITask task = SpringUtil.getBean(jobEntity.getBeanName(), ITask.class);
            task.run(jobEntity.getParams());
            // 0:成功,1:失败
            logEntity.setStatus(0);
        }catch (Exception e){
            logEntity.setStatus(1);
            logEntity.setError(e.toString().substring(0,2000));
        }finally {
            long invokeTime = System.currentTimeMillis() - startTime;
            logEntity.setTime((int)invokeTime);
            logService.save(logEntity);
        }
    }
}
