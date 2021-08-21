package pers.syq.fastadmin.backstage.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import pers.syq.fastadmin.backstage.common.utils.PageUtils;
import pers.syq.fastadmin.backstage.entity.SysMenuEntity;
import pers.syq.fastadmin.backstage.mapper.SysMenuMapper;
import pers.syq.fastadmin.backstage.service.SysMenuService;

import java.util.List;
import java.util.Map;


@Service("sysMenuService")
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenuEntity> implements SysMenuService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SysMenuEntity> page = this.page(PageUtils.getPage(params));
        return new PageUtils(page);
    }

    @Override
    public List<SysMenuEntity> listByUserId(Long userId) {
        return this.baseMapper.selectListByUserId(userId);
    }


}