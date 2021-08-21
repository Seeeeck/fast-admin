package pers.syq.fastadmin.backstage.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import pers.syq.fastadmin.backstage.common.utils.PageUtils;
import pers.syq.fastadmin.backstage.entity.SysRoleEntity;
import pers.syq.fastadmin.backstage.mapper.SysRoleMapper;
import pers.syq.fastadmin.backstage.service.SysRoleService;

import java.util.List;
import java.util.Map;


@Service("sysRoleService")
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRoleEntity> implements SysRoleService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SysRoleEntity> page = this.page(PageUtils.getPage(params));
        return new PageUtils(page);
    }

    @Override
    public List<SysRoleEntity> listByUserId(Long id) {
        return this.baseMapper.selectListByUserId(id);
    }


}