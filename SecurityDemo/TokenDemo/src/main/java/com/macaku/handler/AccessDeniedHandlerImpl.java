package com.macaku.handler;

import com.alibaba.fastjson.JSON;
import com.macaku.common.ResponseResult;
import com.macaku.utils.WebUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
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
 * Time: 14:52
 */
@Component
@Slf4j
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        log.warn(e.getMessage());
        ResponseResult responseResult = new ResponseResult(HttpStatus.FORBIDDEN.value(), "用户授权不足");
        //处理异常
        WebUtils.renderString(httpServletResponse, JSON.toJSONString(responseResult));
    }
}
