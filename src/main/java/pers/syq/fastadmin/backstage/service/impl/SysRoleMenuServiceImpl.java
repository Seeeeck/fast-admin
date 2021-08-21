package pers.syq.fastadmin.backstage.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import pers.syq.fastadmin.backstage.common.utils.PageUtils;
import pers.syq.fastadmin.backstage.entity.SysRoleMenuEntity;
import pers.syq.fastadmin.backstage.mapper.SysRoleMenuMapper;
import pers.syq.fastadmin.backstage.service.SysRoleMenuService;

import java.util.Map;


@Service("sysRoleMenuService")
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper, SysRoleMenuEntity> implements SysRoleMenuService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SysRoleMenuEntity> page = this.page(PageUtils.getPage(params));
        return new PageUtils(page);
    }

}