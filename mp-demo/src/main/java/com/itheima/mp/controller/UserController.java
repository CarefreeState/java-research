package com.itheima.mp.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.itheima.mp.domain.dto.PageDTO;
import com.itheima.mp.domain.dto.UserFormDTO;
import com.itheima.mp.domain.po.User;
import com.itheima.mp.domain.query.UserQuery;
import com.itheima.mp.domain.vo.UserVO;
import com.itheima.mp.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: 马拉圈
 * Date: 2023-12-09
 * Time: 0:53
 */
@Api(tags = "用户管理接口")
@RequestMapping("/users")
@RequiredArgsConstructor
@RestController
public class UserController {

    private final IUserService userService;

    @PostMapping("/")
    @ApiOperation("新增用户接口")
    public void saveUser(UserFormDTO userDTO) {
        User user = BeanUtil.copyProperties(userDTO, User.class);
        userService.save(user);
    }

    @ApiOperation("删除用户接口")
    @DeleteMapping("/{id}")
    public void deleteUserById(@ApiParam("用户id") @PathVariable("id") Long id) {
        Wrapper<User> wrapper = new LambdaQueryWrapper<User>().eq(User::getId, id);
        userService.remove(wrapper);
//        userService.removeById(id);
    }

//    @ApiOperation("根据id查询用户")
//    @GetMapping("/{id}")
//    public UserVO selectUserById(@ApiParam("用户id") @PathVariable("id") Long id) {
//        User user = userService.getById(id);
//        return BeanUtil.copyProperties(user, UserVO.class);
//    }

    @ApiOperation("根据id查询用户")
    @GetMapping("/{id}")
    public UserVO selectUserById(@ApiParam("用户id") @PathVariable("id") Long id) {
        return userService.selectUserAndAddressesById(id);
    }

//    @ApiOperation("根据id批量查询")
//    @GetMapping("/")
//    public List<UserVO> selectUsersByIds(@ApiParam("用户id集合") @RequestParam("ids") List<Long> ids) {
//        List<User> list = userService.listByIds(ids);
//        return BeanUtil.copyToList(list, UserVO.class);
//    }


    @ApiOperation("根据id批量查询")
    @GetMapping("/")
    public List<UserVO> selectUsersByIds(@ApiParam("用户id集合") @RequestParam("ids") List<Long> ids) {
        return userService.selectUserAndAddressesByIds(ids);
    }

    @ApiOperation("根据id扣减用户余额")
    @PutMapping("/{id}/deduction/{money}")
    public void deductMoneyById(@ApiParam("用户id") @PathVariable("id") Long id,
                                @ApiParam("扣减数额") @PathVariable("money") Integer money) {
        userService.deductMoneyById(id, money);
    }

    @ApiOperation("根据复杂条件查询用户")
    @GetMapping("/list")
    public List<UserVO> selectUsers(UserQuery userQuery) {
        // 查询用户
        List<User> users = userService.queryUsers(userQuery.getName(), userQuery.getStatus(), userQuery.getMaxBalance(), userQuery.getMinBalance());
        // 返回
        return BeanUtil.copyToList(users, UserVO.class);
    }

    @ApiOperation("根据复杂条件分页查询用户")
    @GetMapping("/page")
    public PageDTO<UserVO> selectUsersPage(UserQuery userQuery) {

        return userService.queryUsersPage(userQuery);
    }

}
