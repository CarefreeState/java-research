package com.macaku.controller;

import com.macaku.common.ResponseResult;
import com.macaku.domain.query.FormUserDTO;
import com.macaku.service.LoginService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: 马拉圈
 * Date: 2024-01-11
 * Time: 21:26
 */
@RestController
//@RequestMapping("/user")
public class LoginController {


    @Resource
    private LoginService loginService;

    @PostMapping("/user/login")
    public ResponseResult login(@RequestBody FormUserDTO user) {
        //登录
        return loginService.login(user);
    }

    @GetMapping("/user/logout")
    public ResponseResult logout() {
        return loginService.logout();
    }


}
