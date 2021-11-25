package com.zxdmy.excite.admin.service;

import com.zxdmy.excite.system.entity.SysMenu;
import com.zxdmy.excite.system.mapper.SysMenuMapper;
import com.zxdmy.excite.system.service.ISysMenuService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * <p>
 * 描述
 * </p>
 *
 * @author 拾年之璐
 * @since 2021-09-23 0023 20:05
 */
@SpringBootTest
class ISysMenuServiceTest {

    @Autowired
    private ISysMenuService menuService;

    @Autowired
    private SysMenuMapper menuMapper;

    @Test
    void deleteTest() {
        Integer id = null;
        menuService.deleteMenu(id, "list");

    }

    @Test
    void selectTest() {
        Integer id = 10000;
        List<SysMenu> sysMenus = menuMapper.selectMenusByUserId(id);
        System.out.println(sysMenus);

    }


}