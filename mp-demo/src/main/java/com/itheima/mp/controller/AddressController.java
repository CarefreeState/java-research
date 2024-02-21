package com.itheima.mp.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.itheima.mp.domain.po.Address;
import com.itheima.mp.domain.vo.AddressVO;
import com.itheima.mp.service.AddressService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: 马拉圈
 * Date: 2023-12-10
 * Time: 11:05
 */
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/address")
@Api(tags = "地址相关操作")
public class AddressController {

    private final AddressService addressService;

    @ApiOperation("根据id删除地址信息")
    @DeleteMapping("/{id}")
    public void removeById(@PathVariable("id") @ApiParam("地址id") Long id) {
        addressService.removeById(id);
    }

    @ApiOperation("添加地址")
    @PostMapping("/insert")
    public void insertAddress(Address address) {
        Db.save(address);
    }

    @ApiOperation("查询地址信息")
    @GetMapping("{id}")
    public AddressVO queryAddressById(@PathVariable("id") @ApiParam("地址id") Long id) {
        return BeanUtil.copyProperties(Db.getById(id, Address.class), AddressVO.class);
    }

}
