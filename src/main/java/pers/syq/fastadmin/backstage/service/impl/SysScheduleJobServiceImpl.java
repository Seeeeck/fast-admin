package pers.syq.fastadmin.backstage.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.quartz.CronTrigger;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pers.syq.fastadmin.backstage.common.utils.PageUtils;
import pers.syq.fastadmin.backstage.constants.WebConstants;
import pers.syq.fastadmin.backstage.entity.SysScheduleJobEntity;
import pers.syq.fastadmin.backstage.entity.dto.ScheduleJobDTO;
import pers.syq.fastadmin.backstage.mapper.SysScheduleJobMapper;
import pers.syq.fastadmin.backstage.schedule.ScheduleUtils;
import pers.syq.fastadmin.backstage.service.SysScheduleJobService;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class SysScheduleJobServiceImpl extends ServiceImpl<SysScheduleJobMapper, SysScheduleJobEntity> implements SysScheduleJobService {

    @Autowired
    private Scheduler scheduler;

    @PostConstruct
    public void init(){
        List<SysScheduleJobEntity> jobEntities = this.list();
        for (SysScheduleJobEntity jobEntity : jobEntities) {
            CronTrigger cronTrigger = ScheduleUtils.getCronTrigger(scheduler, jobEntity.getId());
            if(cronTrigger == null){
                ScheduleUtils.createScheduleJob(scheduler,jobEntity);
            }else {
                ScheduleUtils.updateScheduleJob(scheduler,jobEntity);
            }
        }
    }

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String beanName = (String)params.get("beanName");
        LambdaQueryWrapper<SysScheduleJobEntity> wrapper = new LambdaQueryWrapper<>();
        if (StrUtil.isNotBlank(beanName)){
            wrapper.like(SysScheduleJobEntity::getBeanName,beanName);
        }
        IPage<SysScheduleJobEntity> page = this.page(PageUtils.getPage(params),wrapper);
        return new PageUtils(page);
    }

    @Override
    @Transactional
    public void removeBatch(List<Long> ids) {
        for (Long id : ids) {
            ScheduleUtils.deleteScheduleJob(scheduler,id);
        }
        this.removeByIds(ids);
    }

    @Override
    @Transactional
    public void run(List<Long> ids) {
        List<SysScheduleJobEntity> jobEntities = this.listByIds(ids);
        for (SysScheduleJobEntity jobEntity : jobEntities) {
            ScheduleUtils.run(scheduler,jobEntity);
        }
    }

    @Override
    @Transactional
    public void pause(List<Long> ids) {
        for (Long id : ids) {
            ScheduleUtils.pauseJob(scheduler,id);
        }
        updateBatch(ids, WebConstants.ScheduleStatus.PAUSE.getValue());
    }

    @Override
    @Transactional
    public void resume(List<Long> ids) {
        for (Long id : ids) {
            ScheduleUtils.resumeJob(scheduler,id);
        }
        updateBatch(ids, WebConstants.ScheduleStatus.NORMAL.getValue());
    }

    private void updateBatch(List<Long> ids, int status) {
        List<SysScheduleJobEntity> jobEntities = ids.stream().map(id -> {
            SysScheduleJobEntity jobEntity = new SysScheduleJobEntity();
            jobEntity.setId(id);
            jobEntity.setStatus(status);
            return jobEntity;
        }).collect(Collectors.toList());
        this.updateBatchById(jobEntities);
    }

    @Override
    @Transactional
    public void updateDTO(ScheduleJobDTO scheduleJobDTO) {
        SysScheduleJobEntity jobEntity = new SysScheduleJobEntity();
        BeanUtil.copyProperties(scheduleJobDTO,jobEntity);
        ScheduleUtils.updateScheduleJob(scheduler,jobEntity);
        this.updateById(jobEntity);
    }

    @Override
    @Transactional
    public void saveDTO(ScheduleJobDTO scheduleJobDTO) {
        SysScheduleJobEntity jobEntity = new SysScheduleJobEntity();
        BeanUtil.copyProperties(scheduleJobDTO,jobEntity);
        jobEntity.setStatus(0);
        this.save(jobEntity);
        ScheduleUtils.createScheduleJob(scheduler,jobEntity);
    }

}