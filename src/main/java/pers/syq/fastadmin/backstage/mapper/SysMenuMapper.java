package pers.syq.fastadmin.backstage.mapper;

import pers.syq.fastadmin.backstage.entity.SysMenuEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 *
 * @author syq
 * @email sunyuqian0211@gmail.com
 * @date 2021-08-16
 */
@Mapper
public interface SysMenuMapper extends BaseMapper<SysMenuEntity> {

    List<SysMenuEntity> selectListByUserId(Long userId);

    List<SysMenuEntity> selectListByRoleId(Long roleId);

    SysMenuEntity selectParentById(Long id);
}
