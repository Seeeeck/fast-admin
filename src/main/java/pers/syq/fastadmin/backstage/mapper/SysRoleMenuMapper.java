package pers.syq.fastadmin.backstage.mapper;

import pers.syq.fastadmin.backstage.entity.IdCountEntity;
import pers.syq.fastadmin.backstage.entity.SysRoleMenuEntity;
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
public interface SysRoleMenuMapper extends BaseMapper<SysRoleMenuEntity> {

    List<IdCountEntity> listIdCount(List<Long> peerMenuIds);
}
