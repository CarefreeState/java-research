package com.macaku.service.impl;

import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.macaku.domain.dto.LoginUser;
import com.macaku.domain.po.SysUser;
import com.macaku.service.SysMenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.security.auth.RefreshFailedException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: 马拉圈
 * Date: 2024-01-11
 * Time: 19:42
 */
@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    @Resource
    private SysMenuService menuService;

    // 查询用户信息
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        // 查询用户信息
        SysUser user = Db.lambdaQuery(SysUser.class)
                .eq(SysUser::getUserName, s)
                .one();
        if(Objects.isNull(user)) {
            log.warn("用户名或者密码错误");
            throw new RuntimeException("用户名或者密码错误");
        }
        // 查询对应的权限信息
        List<String> permissions = menuService.selectMenuById(user.getId());
        // 把数据封装成UserDetails返回
        return new LoginUser(user, permissions);
    }
}
