package pers.syq.fastadmin.backstage.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pers.syq.fastadmin.backstage.common.utils.PageUtils;
import pers.syq.fastadmin.backstage.common.utils.R;
import pers.syq.fastadmin.backstage.service.SysScheduleJobLogService;

import java.util.Map;



/**
 *
 * @author syq
 * @email sunyuqian0211@gmail.com
 * @date 2021-08-29
 */
@RestController
@RequestMapping("sys/schedule-log")
public class SysScheduleJobLogController {
    @Autowired
    private SysScheduleJobLogService sysScheduleJobLogService;


    @ApiOperation(value = "queryPage")
    @PreAuthorize("hasAnyAuthority('sys:schedule:log','ROLE_ADMIN')")
    @GetMapping("/page")
    public R<PageUtils> page(@RequestParam Map<String, Object> params){
        PageUtils page = sysScheduleJobLogService.queryPage(params);
        return R.ok(page);
    }



}
