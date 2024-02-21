package com.macaku.service.impl;

import com.macaku.domain.po.SysMenu;
import com.macaku.service.SysMenuService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: 马拉圈
 * Date: 2024-01-12
 * Time: 17:38
 */
@SpringBootTest
class SysMenuServiceImplTest {

    @Resource
    private SysMenuService sysMenuService;

    @Test
    void selectMenuById() {
        sysMenuService.selectMenuById(2L).forEach(System.out::println);
    }
}