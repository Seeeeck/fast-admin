package pers.syq.fastadmin.backstage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import pers.syq.fastadmin.backstage.common.utils.PageUtils;
import pers.syq.fastadmin.backstage.entity.SysScheduleJobLogEntity;

import java.util.Map;

/**
 *
 * @author syq
 * @email sunyuqian0211@gmail.com
 * @date 2021-08-29
 */
public interface SysScheduleJobLogService extends IService<SysScheduleJobLogEntity> {
    PageUtils queryPage(Map<String, Object> params);
}

