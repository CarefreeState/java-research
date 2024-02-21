package com.itheima.mp.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.itheima.mp.common.UserStatus;
import com.itheima.mp.domain.dto.PageDTO;
import com.itheima.mp.domain.po.Address;
import com.itheima.mp.domain.po.User;
import com.itheima.mp.domain.query.UserQuery;
import com.itheima.mp.domain.vo.AddressVO;
import com.itheima.mp.domain.vo.UserVO;
import com.itheima.mp.mapper.UserMapper;
import com.itheima.mp.service.AddressService;
import com.itheima.mp.service.IUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: 马拉圈
 * Date: 2023-12-08
 * Time: 23:46
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    private final UserMapper userMapper;

    private final AddressService addressService;

    @Override
    public void deductMoneyById(Long id, Integer money) {
        // 1. 查询用户
        User user = this.getById(id);
        // 2. 校验用户状态
        if(user == null || user.getStatus() == UserStatus.FROZEN) {
            log.warn("用户状态异常");
            throw new RuntimeException("用户状态异常");
        }
        // 3. 校验余额是否充足
        if(user.getBalance().compareTo(money) < 0) {
            log.warn("用户余额不足");
            throw new RuntimeException("用户余额不足");
        }
        // 4. 扣减余额
        int remain = user.getBalance() - money;
        lambdaUpdate()
                .set(User::getBalance, remain)
                .set(remain == 0, User::getStatus, UserStatus.FROZEN)
                .eq(User::getId, id)
                .eq(User::getBalance, user.getBalance())
                .update()
        ;
    }
//
//    @Override
//    public void deductMoneyById(Long id, Integer money) {
//        // 1. 查询用户
//        User user = this.getById(id);
//        // 2. 校验用户状态
//        if(user == null || user.getStatus() == UserStatus.FROZEN) {
//            log.warn("用户状态异常");
//            throw new RuntimeException("用户状态异常");
//        }
//        // 3. 校验余额是否充足
//        if(user.getBalance().compareTo(money) < 0) {
//            log.warn("用户余额不足");
//            throw new RuntimeException("用户余额不足");
//        }
//        // 4. 扣减余额
//        Wrapper<User> wrapper = new LambdaUpdateWrapper<User>().eq(User::getId, id);
//        userMapper.updateBalance(wrapper, money);
//    }

    @Override
    public List<User> queryUsers(String name, UserStatus status, Integer maxBalance, Integer minBalance) {
        return lambdaQuery()
                .like(name != null, User::getUsername, name)
                .eq(status != null, User::getStatus, status)
//                .between(minBalance != null && maxBalance != null, User::getBalance, minBalance, maxBalance)
                .ge(minBalance != null, User::getBalance, minBalance)
                .le(maxBalance != null, User::getBalance, maxBalance)
                .list();
    }

    @Override
    public UserVO selectUserAndAddressesById(Long id) {
        // 查询user
        User user = this.getById(id);
        if(user == null || user.getStatus() == UserStatus.FROZEN) {
            log.warn("用户状态异常");
            throw new RuntimeException("用户状态异常");
        }
        // 根据user的id查询地址
        Wrapper<Address> wrapper = new LambdaQueryWrapper<Address>().eq(Address::getUserId, user.getId());
//        List<Address> list = addressService.list(wrapper);
//        List<Address> list = addressService.lambdaQuery().eq(Address::getUserId, user.getId()).list();
        List<Address> list = Db.lambdaQuery(Address.class).eq(Address::getUserId, user.getId()).list();
        list = list == null ? Collections.emptyList() : list;
        // 设置
        UserVO userVO = BeanUtil.copyProperties(user, UserVO.class);
        userVO.setAddresses(BeanUtil.copyToList(list, AddressVO.class));
        return userVO;
    }

    @Override
    public List<UserVO> selectUserAndAddressesByIds(List<Long> ids) {
        // 查询用户列表
        List<User> users = this.listByIds(ids);
        // 校验
        if(CollUtil.isEmpty(users)) {
            log.warn("用户找不到");
            throw new RuntimeException("用户找不到");
        }
        // 设置
        List<Long> realIds = users.parallelStream().map(User::getId).collect(Collectors.toList());
        List<Address> addresses = Db.lambdaQuery(Address.class).in(Address::getUserId, realIds).list();
        // 分组
        Map<Long, List<AddressVO>> addressMap = addresses.stream()
                .map(address -> BeanUtil.copyProperties(address, AddressVO.class))
                .collect(Collectors.groupingBy(AddressVO::getUserId));
        return users.parallelStream()
                .map(user -> BeanUtil.copyProperties(user, UserVO.class))
                .peek(userVO -> userVO.setAddresses(addressMap.get(userVO.getId())))
                .collect(Collectors.toList())
        ;

//        return users.parallelStream()
//                .map(user -> BeanUtil.copyProperties(user, UserVO.class))
//                .peek(userVO -> {
//                    List<Address> addressList = Db.lambdaQuery(Address.class).eq(Address::getUserId, userVO.getId()).list();
//                    //这种可能效率会比较低，因为提交多次查询sql！
//                    userVO.setAddresses(BeanUtil.copyToList(addressList, AddressVO.class));
//                })
//                .collect(Collectors.toList())
//        ;
    }

    @Override
    public PageDTO<UserVO> queryUsersPage(UserQuery userQuery) {
        // 1. 构造查询条件
        String name = userQuery.getName();
        UserStatus status = userQuery.getStatus();
        Integer minBalance = userQuery.getMinBalance();
        Integer maxBalance = userQuery.getMaxBalance();
        Page<User> page  = userQuery.toMpPage();
        // 2. 分页查询
        this.lambdaQuery()
                .like(name != null, User::getUsername, name)
                .eq(status != null, User::getStatus, status)
//                .between(minBalance != null && maxBalance != null, User::getBalance, minBalance, maxBalance)
                .ge(minBalance != null, User::getBalance, minBalance)
                .le(maxBalance != null, User::getBalance, maxBalance)
                .page(page);
        // 3. 封装结果
//        PageDTO<UserVO> result = new PageDTO<>();
//        result.setPages(page.getPages());
//        result.setTotal(page.getTotal());
//        result.setList(BeanUtil.copyToList(page.getRecords(), UserVO.class));
//        PageDTO<UserVO> result = PageDTO.of(page, UserVO.class);
        PageDTO<UserVO> result = PageDTO.of(page, (User user) -> {
            UserVO userVO = BeanUtil.copyProperties(user, UserVO.class);
            userVO.setUsername(userVO.getUsername().charAt(0) + "*");
            return userVO;
        });
        // 4. 返回
        return result;

    }


}
