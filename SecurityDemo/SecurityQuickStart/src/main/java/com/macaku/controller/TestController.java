package com.macaku.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: 马拉圈
 * Date: 2024-01-11
 * Time: 18:29
 */
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/test")
public class TestController {

    @GetMapping("/hello")
    public String hello() {
        return SecurityContextHolder.getContext().getAuthentication().toString();
    }

}
