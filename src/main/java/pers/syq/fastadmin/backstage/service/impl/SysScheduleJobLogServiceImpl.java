package pers.syq.fastadmin.backstage.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import pers.syq.fastadmin.backstage.common.utils.PageUtils;
import pers.syq.fastadmin.backstage.entity.SysScheduleJobLogEntity;
import pers.syq.fastadmin.backstage.mapper.SysScheduleJobLogMapper;
import pers.syq.fastadmin.backstage.service.SysScheduleJobLogService;

import java.util.Map;


@Service
public class SysScheduleJobLogServiceImpl extends ServiceImpl<SysScheduleJobLogMapper, SysScheduleJobLogEntity> implements SysScheduleJobLogService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String jobId = (String)params.get("jobId");
        LambdaQueryWrapper<SysScheduleJobLogEntity> wrapper = new LambdaQueryWrapper<>();
        if (StrUtil.isNotBlank(jobId)){
            wrapper.eq(SysScheduleJobLogEntity::getJobId,jobId);
        }
        IPage<SysScheduleJobLogEntity> page = this.page(PageUtils.getPage(params),wrapper);
        return new PageUtils(page);
    }

}