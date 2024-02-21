package com.macaku.service;

import com.macaku.domain.po.SysMenu                                                                                                                                                                                         ;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author 马拉圈
* @description 针对表【sys_menu(菜单表)】的数据库操作Service
* @createDate 2024-01-12 16:59:33
*/
public interface SysMenuService extends IService<SysMenu> {
    List<String> selectMenuById(Long id);

}
