package com.macaku.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.macaku.domain.po.SysUser                                                                                                                                                                                         ;
import com.macaku.service.SysUserService;
import com.macaku.mapper.SysUserMapper;
import org.springframework.stereotype.Service;

/**
* @author 马拉圈
* @description 针对表【sys_user(用户表)】的数据库操作Service实现
* @createDate 2024-01-11 19:04:38
*/
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser>
    implements SysUserService{

}




