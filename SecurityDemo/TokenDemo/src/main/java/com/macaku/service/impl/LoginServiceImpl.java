package com.macaku.service.impl;

import com.macaku.common.ResponseResult;
import com.macaku.domain.dto.LoginUser;
import com.macaku.domain.query.FormUserDTO;
import com.macaku.service.LoginService;
import com.macaku.utils.JwtUtil;
import com.macaku.utils.RedisCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: 马拉圈
 * Date: 2024-01-11
 * Time: 21:35
 */
@Slf4j
@Service
public class LoginServiceImpl implements LoginService {

    @Resource
    private AuthenticationManager authenticationManager;

    @Resource
    private RedisCache redisCache;

    @Override
    public ResponseResult<Map<String, String>> login(FormUserDTO user) {
        // 获取AuthenticationManager authenticate认证
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        //如果认证不通过，提示
        if(Objects.isNull(authenticate)) {
            log.warn("登录失败");
            throw new RuntimeException("登录失败");
        }
        //如果认证通过了，用userid生成一个jwt
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();//认证的时候已经调用了getAuthorities了
        String userid = loginUser.getUser().getId().toString();
        String jwt = JwtUtil.createJWT(userid);
        //把完整的用户信息存入redis，userid作为key
        redisCache.setCacheObject("login:" + userid, loginUser);
//        redisCache.getCacheObject("login:" + userid);//是完完整整的存进去的
        // 符合jwt
        return new ResponseResult(200, "登录成功", new HashMap<String, String>(){{
            this.put("token", jwt);
        }});
    }

    @Override
    public ResponseResult logout() {
        //获取用户id
        UsernamePasswordAuthenticationToken authentication
                = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        String userid = loginUser.getUser().getId().toString();
        // 删除redis中的值
        String redisKey = "login:" + userid;
        redisCache.deleteObject(redisKey);
        return new ResponseResult(200,  "注销成功");
    }
}
