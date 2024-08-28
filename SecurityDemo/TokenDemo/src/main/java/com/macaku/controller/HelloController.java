package com.macaku.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: 马拉圈
 * Date: 2024-01-11
 * Time: 15:20
 */
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/test")
public class HelloController {

    @GetMapping("/hello")
//    @PreAuthorize("hasAuthority('system:test:list')")
    @PreAuthorize("@mex.hasAuthority('system:test:list')")//SPEL 表达式中，这代表调用自己的 bean 为 mex 的对象的 hasAuthority 方法
    public String hello() {
        return "<h1>hello!</h1>";
    }

    @GetMapping("/hi")
//    @PreAuthorize("hasAuthority('system:dept:list')")
//    @PreAuthorize("hasAuthority('system:dept:balabala')")
//    @PreAuthorize("hasRole('system:dept:list')")//拼接ROLE_前缀 后去验证
    public String hi() {
        return SecurityContextHolder.getContext().getAuthentication().toString();
    }

    @GetMapping("/haha")
    @PreAuthorize("hasAnyAuthority('system:dept:list', 'haha')")
//    @PreAuthorize("hasAnyRole('system:dept:list', 'haha')")//拼接ROLE_前缀 后去验证
    public String haha() {
        return "<h1>haha!</h1>";
    }
}
