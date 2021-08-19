package pers.syq.fastadmin.backstage.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import pers.syq.fastadmin.backstage.common.utils.PageUtils;

import pers.syq.fastadmin.backstage.mapper.SysRoleMenuMapper;
import pers.syq.fastadmin.backstage.entity.SysRoleMenuEntity;
import pers.syq.fastadmin.backstage.service.SysRoleMenuService;


@Service("sysRoleMenuService")
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper, SysRoleMenuEntity> implements SysRoleMenuService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SysRoleMenuEntity> page = this.page(getPage(params), new QueryWrapper<SysRoleMenuEntity>());
        return new PageUtils(page);
    }

    private IPage<SysRoleMenuEntity> getPage(Map<String, Object> params){
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