package com.itheima.mp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.mp.common.UserStatus;
import com.itheima.mp.domain.dto.PageDTO;
import com.itheima.mp.domain.po.User;
import com.itheima.mp.domain.query.UserQuery;
import com.itheima.mp.domain.vo.UserVO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: 马拉圈
 * Date: 2023-12-08
 * Time: 23:45
 */
public interface IUserService extends IService<User> {

    @Transactional
    void deductMoneyById(Long id, Integer money);


    List<User> queryUsers(String name, UserStatus status, Integer maxBalance, Integer minBalance);


    UserVO selectUserAndAddressesById(Long id);

    List<UserVO> selectUserAndAddressesByIds(List<Long> ids);

    PageDTO<UserVO> queryUsersPage(UserQuery userQuery);
}
