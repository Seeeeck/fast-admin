package pers.syq.fastadmin.backstage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import pers.syq.fastadmin.backstage.common.utils.PageUtils;
import pers.syq.fastadmin.backstage.entity.SysScheduleJobEntity;
import pers.syq.fastadmin.backstage.entity.dto.ScheduleJobDTO;

import java.util.List;
import java.util.Map;

/**
 *
 * @author syq
 * @email sunyuqian0211@gmail.com
 * @date 2021-08-29
 */
public interface SysScheduleJobService extends IService<SysScheduleJobEntity> {
    PageUtils queryPage(Map<String, Object> params);

    void removeBatch(List<Long> ids);

    void run(List<Long> ids);

    void pause(List<Long> ids);

    void resume(List<Long> ids);

    void updateDTO(ScheduleJobDTO scheduleJobDTO);

    void saveDTO(ScheduleJobDTO scheduleJobDTO);
}

