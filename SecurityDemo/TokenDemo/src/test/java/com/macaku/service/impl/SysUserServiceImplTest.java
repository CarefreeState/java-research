package com.macaku.service.impl;

import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.macaku.config.SecurityConfig;
import com.macaku.domain.po.SysUser;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: 马拉圈
 * Date: 2024-01-11
 * Time: 19:20
 */
@SpringBootTest
class
SysUserServiceImplTest {


    @Resource
    private SecurityConfig securityConfig;

    @Test
    public void bCryptPasswordEncoder() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encode1 = bCryptPasswordEncoder.encode("123456");
        System.out.println();
        String encode2 = bCryptPasswordEncoder.encode("123456");

        System.out.println(encode1);
        System.out.println(bCryptPasswordEncoder.matches("1234", encode1));
        System.out.println(encode2);
        System.out.println(bCryptPasswordEncoder.matches("1234", encode2));
    }

    @Test
    public void getAllUser() {
        System.out.println(Db.list(SysUser.class));
    }

    @Resource
    private PasswordEncoder encoder;

    @Test
    public void createUser() {
        List<SysUser> list = new ArrayList<SysUser>() {{
            add(new SysUser(){{
                setNickName("马1号");
                setUserName("NONE_PROVIDED");
                setPassword(encoder.encode("123456"));
            }});
            add(new SysUser(){{
                setNickName("马2号");
                setUserName("NONE_PROVIDED2");
                setPassword(encoder.encode("123456"));
            }});
        }};
        Db.saveBatch(list);
    }

}