package com.itheima.mp.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.itheima.mp.domain.po.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper extends BaseMapper<User> {

    List<User> queryUserByIds(@Param("ids") List<Long> ids);

    void updateBalance(@Param(Constants.WRAPPER) Wrapper<User> wrapper, @Param("amount") Integer amount);
}
