package pers.syq.fastadmin.backstage.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pers.syq.fastadmin.backstage.annotation.SysLog;
import pers.syq.fastadmin.backstage.common.utils.PageUtils;
import pers.syq.fastadmin.backstage.common.utils.R;
import pers.syq.fastadmin.backstage.common.utils.Save;
import pers.syq.fastadmin.backstage.entity.SysScheduleJobEntity;
import pers.syq.fastadmin.backstage.entity.dto.ScheduleJobDTO;
import pers.syq.fastadmin.backstage.service.SysScheduleJobService;

import java.util.List;
import java.util.Map;



/**
 *
 * @author syq
 * @email sunyuqian0211@gmail.com
 * @date 2021-08-29
 */
@RestController
@RequestMapping("sys/schedule")
public class SysScheduleJobController {
    @Autowired
    private SysScheduleJobService sysScheduleJobService;


    @ApiOperation(value = "queryPage")
    @PreAuthorize("hasAnyAuthority('sys:schedule:page','ROLE_ADMIN')")
    @GetMapping("/page")
    public R<PageUtils> page(@RequestParam Map<String, Object> params){
        PageUtils page = sysScheduleJobService.queryPage(params);
        return R.ok(page);
    }

    @ApiOperation(value = "getById")
    @PreAuthorize("hasAnyAuthority('sys:schedule:get','ROLE_ADMIN')")
    @GetMapping("/{id}")
    public R<SysScheduleJobEntity> getById(@PathVariable("id") Long id){
		SysScheduleJobEntity sysScheduleJob = sysScheduleJobService.getById(id);
        return R.ok(sysScheduleJob);
    }

    @SysLog("Create job")
    @ApiOperation(value = "save")
    @PreAuthorize("hasAnyAuthority('sys:schedule:save','ROLE_ADMIN')")
    @PostMapping
    public R<?> save(@RequestBody @Validated(Save.class) ScheduleJobDTO scheduleJobDTO){
		sysScheduleJobService.saveDTO(scheduleJobDTO);
        return R.ok();
    }

    @SysLog("Update job")
    @ApiOperation(value = "update")
    @PreAuthorize("hasAnyAuthority('sys:schedule:update','ROLE_ADMIN')")
    @PutMapping
    public R<?> update(@RequestBody @Validated(Save.class) ScheduleJobDTO scheduleJobDTO){
		sysScheduleJobService.updateDTO(scheduleJobDTO);
        return R.ok();
    }

    @SysLog("Delete job")
    @ApiOperation(value = "deleteBatch")
    @PreAuthorize("hasAnyAuthority('sys:schedule:delete','ROLE_ADMIN')")
    @DeleteMapping("/batch")
    public R<?> deleteBatch(@RequestParam("ids") List<Long> ids){
		sysScheduleJobService.removeBatch(ids);
        return R.ok();
    }

    @SysLog("Invoke job")
    @PostMapping("/run")
    @PreAuthorize("hasAnyAuthority('sys:schedule:run','ROLE_ADMIN')")
    public R<?> run(@RequestParam("ids") List<Long> ids){
        sysScheduleJobService.run(ids);
        return R.ok();
    }

    @SysLog("Pause job")
    @PostMapping("/pause")
    @PreAuthorize("hasAnyAuthority('sys:schedule:pause','ROLE_ADMIN')")
    public R<?> pause(@RequestParam("ids") List<Long> ids){
        sysScheduleJobService.pause(ids);
        return R.ok();
    }

    @SysLog("Resume job")
    @PostMapping("/resume")
    @PreAuthorize("hasAnyAuthority('sys:schedule:resume','ROLE_ADMIN')")
    public R<?> resume(@RequestParam("ids") List<Long> ids){
        sysScheduleJobService.resume(ids);
        return R.ok();
    }



}
