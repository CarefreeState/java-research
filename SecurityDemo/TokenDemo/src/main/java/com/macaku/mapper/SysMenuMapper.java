package com.macaku.mapper;

import com.macaku.domain.po.SysMenu                                                                                                                                                                                         ;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author 马拉圈
* @description 针对表【sys_menu(菜单表)】的数据库操作Mapper
* @createDate 2024-01-12 16:59:33
* @Entity com.macaku.domain.po.SysMenu                                                                                                                                                                                         
*/
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    List<SysMenu> selectMenuById(@Param("id") Long id);
}




