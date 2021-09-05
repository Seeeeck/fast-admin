package pers.syq.fastadmin.backstage.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pers.syq.fastadmin.backstage.entity.SysMenuEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class SysMenuMapperTest {

    @Autowired
    private SysMenuMapper mapper;

    @Test
    void selectListByUserId() {
        List<SysMenuEntity> menuEntities = mapper.selectListByUserId(1L);

    }
}