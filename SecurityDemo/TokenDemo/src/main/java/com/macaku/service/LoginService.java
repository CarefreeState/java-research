package com.macaku.service;

import com.macaku.common.ResponseResult;
import com.macaku.domain.query.FormUserDTO;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: 马拉圈
 * Date: 2024-01-11
 * Time: 21:35
 */
public interface LoginService {
    ResponseResult login(FormUserDTO user);

    ResponseResult logout();
}
