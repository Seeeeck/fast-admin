package pers.syq.fastadmin.backstage.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import pers.syq.fastadmin.backstage.common.utils.PageUtils;

import pers.syq.fastadmin.backstage.mapper.SysUserRoleMapper;
import pers.syq.fastadmin.backstage.entity.SysUserRoleEntity;
import pers.syq.fastadmin.backstage.service.SysUserRoleService;


@Service("sysUserRoleService")
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRoleEntity> implements SysUserRoleService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SysUserRoleEntity> page = this.page(getPage(params), new QueryWrapper<SysUserRoleEntity>());
        return new PageUtils(page);
    }

    private IPage<SysUserRoleEntity> getPage(Map<String, Object> params){
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