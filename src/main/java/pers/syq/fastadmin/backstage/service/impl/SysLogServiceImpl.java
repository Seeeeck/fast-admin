package pers.syq.fastadmin.backstage.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import pers.syq.fastadmin.backstage.common.utils.PageUtils;
import pers.syq.fastadmin.backstage.entity.SysLogEntity;
import pers.syq.fastadmin.backstage.mapper.SysLogMapper;
import pers.syq.fastadmin.backstage.service.SysLogService;

import java.util.Map;


@Service("sysLogService")
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLogEntity> implements SysLogService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        LambdaQueryWrapper<SysLogEntity> wrapper = new LambdaQueryWrapper<>();
        String username = (String)params.get("username");
        if (StrUtil.isNotBlank(username)){
            wrapper.like(SysLogEntity::getUsername,username);
        }
        IPage<SysLogEntity> page = this.page(PageUtils.getPage(params), wrapper);
        return new PageUtils(page);
    }



}