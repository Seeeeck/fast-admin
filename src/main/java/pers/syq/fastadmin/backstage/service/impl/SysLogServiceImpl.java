package pers.syq.fastadmin.backstage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import pers.syq.fastadmin.backstage.entity.SysLogEntity;
import pers.syq.fastadmin.backstage.mapper.SysLogMapper;
import pers.syq.fastadmin.backstage.service.SysLogService;
import pers.syq.fastadmin.backstage.common.utils.PageUtils;

import java.util.Map;


@Service("sysLogService")
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLogEntity> implements SysLogService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SysLogEntity> page = this.page(getPage(params), new QueryWrapper<SysLogEntity>());
        return new PageUtils(page);
    }

    private IPage<SysLogEntity> getPage(Map<String, Object> params){
        long curPage = 1;
        long size = 10;
        if(params.get("page") != null){
            curPage = Long.parseLong((String)params.get("page"));
        }
        if(params.get("size") != null){
            size = Long.parseLong((String)params.get("size"));
        }
        return new Page<>(curPage, size);
    }

}