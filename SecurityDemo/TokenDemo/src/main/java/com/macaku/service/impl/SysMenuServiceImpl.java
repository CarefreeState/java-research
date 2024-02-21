package com.macaku.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.macaku.domain.po.SysMenu                                                                                                                                                                                         ;
import com.macaku.service.SysMenuService;
import com.macaku.mapper.SysMenuMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
* @author 马拉圈
* @description 针对表【sys_menu(菜单表)】的数据库操作Service实现
* @createDate 2024-01-12 16:59:33
*/
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu>
    implements SysMenuService{

    @Resource
    private SysMenuMapper sysMenuMapper;

    @Override
    public List<String> selectMenuById(Long id) {
        return sysMenuMapper.selectMenuById(id).stream()
                .map(SysMenu::getPerms)
                .collect(Collectors.toList());

    }
}




