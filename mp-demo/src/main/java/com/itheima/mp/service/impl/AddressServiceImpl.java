package com.itheima.mp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.itheima.mp.domain.po.Address;
import com.itheima.mp.service.AddressService;
import com.itheima.mp.mapper.AddressMapper;
import org.springframework.stereotype.Service;

/**
* @author 马拉圈
* @description 针对表【address】的数据库操作Service实现
* @createDate 2023-12-09 16:58:11
*/
@Service
public class AddressServiceImpl extends ServiceImpl<AddressMapper, Address>
    implements AddressService{

}




