package com.macaku.component;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: 马拉圈
 * Date: 2024-01-13
 * Time: 16:10
 */
@Component("mex")
public class MacakuExpressionRoot {

    public boolean hasAuthority(String authority) {
        // 获取当前用户的权限
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //判断用户权限集合中是否存在authority
        return  authentication.getAuthorities()
                .stream()
                .map(x -> x.getAuthority())
                .distinct()
                .collect(Collectors.toList())
                .contains(authority);
    }

}
