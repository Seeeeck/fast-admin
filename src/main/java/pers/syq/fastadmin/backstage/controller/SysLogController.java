package pers.syq.fastadmin.backstage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pers.syq.fastadmin.backstage.common.utils.PageUtils;
import pers.syq.fastadmin.backstage.common.utils.R;
import pers.syq.fastadmin.backstage.entity.SysLogEntity;
import pers.syq.fastadmin.backstage.service.SysLogService;

import java.util.List;
import java.util.Map;



/**
 *
 * @author syq
 * @email sunyuqian0211@gmail.com
 * @date 2021-08-16
 */
@RestController
@RequestMapping("sys/log")
public class SysLogController {
    @Autowired
    private SysLogService sysLogService;

    @GetMapping
    public R<PageUtils> list(@RequestParam Map<String, Object> params){
        PageUtils page = sysLogService.queryPage(params);
        return R.ok(page);
    }

    @GetMapping("/{id}")
    public R<SysLogEntity> getById(@PathVariable("id") Long id){
		SysLogEntity sysLog = sysLogService.getById(id);
        return R.ok(sysLog);
    }

    @PostMapping
    public R<?> save(@RequestBody SysLogEntity sysLog){
		sysLogService.save(sysLog);
        return R.ok();
    }

    @PutMapping
    public R<?> update(@RequestBody SysLogEntity sysLog){
		sysLogService.updateById(sysLog);
        return R.ok();
    }


    @DeleteMapping("/batch")
    public R<?> deleteBatch(@RequestBody List<Long> ids){
		sysLogService.removeByIds(ids);
        return R.ok();
    }

    @DeleteMapping("/{id}")
    public R<?> deleteById(@PathVariable("id") Long id){
        sysLogService.removeById(id);
        return R.ok();
    }
}
