package com.itheima.mp.service;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itheima.mp.domain.po.User;
import com.itheima.mp.domain.po.UserInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: 马拉圈
 * Date: 2023-12-08
 * Time: 23:51
 */
@SpringBootTest
class IUserServiceTest {

    @Autowired
    private IUserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testSaveUser() throws IOException {
        User user = new User();
//        user.setId(144L);
        user.setUsername("Mao12345");
        user.setPassword("123");
        user.setPhone("18688990011");
        user.setBalance(200);
        user.setInfo(objectMapper.readValue("{\"age\": 24, \"intro\": \"英文老师\", \"gender\": \"female\"}".getBytes(StandardCharsets.UTF_8), UserInfo.class));
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        userService.save(user);
        System.out.println(user);
    }

    @Test
    void testQuery() {
        List<Long> list = new ArrayList<Long>(){{
            add(1L);
            add(2L);
            add(3L);
            add(4L);
            add(5L);
        }};
        List<User> users = userService.listByIds(list);
        users.forEach(System.out::println);
    }

    @Test
    void testPage(){
        int pageNo = 2;//页码是从1开始的
        int pageSize = 2;
        // 准备分页条件
        Page<User> page = Page.of(pageNo, pageSize);
        // 排序规则，注意添加规则的顺序
        page.addOrder(new OrderItem("balance", true));
        page.addOrder(new OrderItem("id", true));
        // 分页
        Page<User> userPage = userService.page(page);//作用于page，并没有拷贝一份再操作
        System.out.println(userPage == page);//是同一个对象
        // 解析
        System.out.println("条数：" + userPage.getTotal());
        System.out.println("总页数：" + userPage.getPages());
        System.out.println("分页数据：");
        List<User> records = userPage.getRecords();
        records.forEach(System.out::println);
    }


    @Test
    void testSaveOneByOne() {
        long b = System.currentTimeMillis();
        for (int i = 0; i < 10_0000; i++) {
            userService.save(buildUser(i));
        }
        System.out.println("耗时：" + (System.currentTimeMillis() - b));
    }

    private User buildUser(int i) {
        User user = new User();
        user.setUsername("Ma" + i);
        user.setPassword("123");
        user.setPhone("186" + String.format("%6d",i));
        user.setBalance(2000);
        user.setInfo(UserInfo.of(24, "英语老师", "female"));
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        return user;
    }

    @Test
    void testSaveBatch() {
        List<User> list = new ArrayList<>(1000);
        long b = System.currentTimeMillis();
        for (int i = 1; i <= 10_0000; i++) {
            list.add(buildUser(i));
            if(i % 1000 == 0) {
                userService.saveBatch(list);
                list.clear();
            }
        }
        System.out.println("耗时：" + (System.currentTimeMillis() - b));

    }

    @Test
    void selectUserAndAddressesById() {

        userService.selectUserAndAddressesById(3L);// 虽然

    }

    @Test
    void selectUserAndAddressesByIds() {
        List<Long> ids = Stream.of(1L, 2L, 3L).collect(Collectors.toList());
        System.out.println(userService.selectUserAndAddressesByIds(ids));// 虽然
    }
}