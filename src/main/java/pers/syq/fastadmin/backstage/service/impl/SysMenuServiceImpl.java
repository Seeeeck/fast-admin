package pers.syq.fastadmin.backstage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import pers.syq.fastadmin.backstage.entity.SysMenuEntity;
import pers.syq.fastadmin.backstage.mapper.SysMenuMapper;
import pers.syq.fastadmin.backstage.service.SysMenuService;
import pers.syq.fastadmin.backstage.common.utils.PageUtils;

import java.util.List;
import java.util.Map;


@Service("sysMenuService")
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenuEntity> implements SysMenuService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SysMenuEntity> page = this.page(getPage(params), new QueryWrapper<SysMenuEntity>());
        return new PageUtils(page);
    }

    @Override
    public List<SysMenuEntity> listByUserId(Long userId) {
        return this.baseMapper.selectListByUserId(userId);
    }

    private IPage<SysMenuEntity> getPage(Map<String, Object> params){
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