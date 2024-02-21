package com.macaku.handler;

import com.alibaba.fastjson.JSON;
import com.macaku.common.ResponseResult;
import com.macaku.utils.WebUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: 马拉圈
 * Date: 2024-01-13
 * Time: 14:44
 */
@Component
@Slf4j
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {//
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        log.warn(e.getMessage());
        ResponseResult responseResult = new ResponseResult(HttpStatus.UNAUTHORIZED.value(), "用户认证失败请查询登录");
        //处理异常
        WebUtils.renderString(httpServletResponse, JSON.toJSONString(responseResult));
    }
}
