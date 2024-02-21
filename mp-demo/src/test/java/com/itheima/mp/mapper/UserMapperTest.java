package com.itheima.mp.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itheima.mp.domain.po.User;
import com.itheima.mp.domain.po.UserInfo;
import com.sun.corba.se.spi.ior.IdentifiableContainerBase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootTest
class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testInsert() throws JsonProcessingException {
        User user = new User();
//        user.setId(144L);
        user.setUsername("Mao1234");
        user.setPassword("123");
        user.setPhone("18688990011");
        user.setBalance(200);
//        user.setInfo((objectMapper.readTree("{\"age\": 24, \"intro\": \"英文老师\", \"gender\": \"female\"}")));
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
//        userMapper.saveUser(user);
        userMapper.insert(user);
        System.out.println(user);//类型为auto，那么就会把自增后的填到主键属性里

    }

    @Test
    void testSelectById() {
//        User user = userMapper.queryUserById(5L);
        User user = userMapper.selectById(145L);
        System.out.println("user = " + user);
    }


    @Test
    void testQueryByIds() {
        List<User> users1 = userMapper.selectBatchIds(Stream.of(1L, 2L, 3L, 4L, 5L, 6L).collect(Collectors.toList()));
        List<User> users2 = userMapper.queryUserByIds(Stream.of(1L, 2L, 3L, 4L, 5L, 6L).collect(Collectors.toList()));
        users1.forEach(System.out::println);
        users2.forEach(System.out::println);
    }

    @Test
    void testUpdateById() {
        User user = new User();
        user.setId(4L);
        user.setBalance(1000);
//        userMapper.updateUser(user);
        userMapper.updateById(user);
    }

    @Test
    void testDeleteUser() {
//        userMapper.deleteUser(5L);
        userMapper.deleteById(5L);
    }

    @Test
    void testWrapper1() {
        // 创建查询语句
        QueryWrapper<User> queryWrapper = new QueryWrapper<User>()
                .select("id, count(*), username", "info", "balance")
                .ge("balance", 1000)
                .like("username", "o");
        // 查询
        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }
    @Test
    void testLambdaWrapper1() {
        // 创建查询语句
        Wrapper<User> wrapper = new LambdaQueryWrapper<User>()
                .select(User::getId, User::getBalance, User::getInfo, User::getUsername)//若没有这个，则是全部查询
                //只能用这种【方法引用】的方式去写，否则就会提示 Didn't start with 'is', 'get' or 'set'
                // 主要是用lambda表达式的写法去写的话，是返回值，通过返回值是无法判断其属性是哪个的
                // 而这里传了个方法，可能就可以被识别到哪个属性的get方法了
                .like(User::getUsername, "o")
                .ge(User::getBalance, 1000)
                ;
        // 查询
        List<User> users = userMapper.selectList(wrapper);
        users.forEach(System.out::println);
    }

    @Test
    void testWrapper2() {
        User updateUser = new User();
        updateUser.setBalance(112200);
        QueryWrapper<User> wrapper = new QueryWrapper<User>().eq("username", "jack");
//        UpdateWrapper<User> wrapper = new UpdateWrapper<User>().eq("username", "jack");
        // 更新
        userMapper.update(updateUser, wrapper);
    }

    @Test
    void testWrapper3() {
        User updateUser = new User();
//        updateUser.setInfo("[]");//info是json格式，必须是json格式字符串才行
        List<Long> list = new ArrayList<Long>(){{
            add(1L);
            add(2L);
            add(4L);
        }};
        Wrapper<User> wrapper = new UpdateWrapper<User>()
                .setSql("balance = balance - 200")//在原对象的修改上，添加修改的sql
                .in("id", list);//list或者其他集合数组，传递给Object...，并不会当成一个object，而是传递一个object数组/集合
                                // 但是如果传递的是两个集合/数组，则会被当做一个object对象
        userMapper.update(updateUser, wrapper);
    }


    @Test
    void testWrapper4() {
        List<Long> list = new ArrayList<Long>(){{
            add(1L);
            add(2L);
            add(4L);
        }};
        int amount = 200;
        Wrapper<User> wrapper = new LambdaUpdateWrapper<User>()
                .setSql("info = {}")//在原对象的修改上，添加修改的sql，但是如果是自定义sql的话，则只取条件where部分，所以这一部分会被省略
                .in(User::getId, list);
//        userMapper.update(null, wrapper);
        // 调用自定义方法
        userMapper.updateBalance(wrapper, amount);

    }


}