package com.itheima.mp.domain.vo;

import com.itheima.mp.common.UserStatus;
import com.itheima.mp.domain.po.UserInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(description = "用户VO实体")
public class UserVO {

    @ApiModelProperty("用户id")
    private Long id;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("详细信息")
    private UserInfo info;
    //如果写成string的话，userinfo对象依然会转化为json赋值到这里面
    //beanUtil在转化的时候，会将属性名一致的尽可能的转化，这里object->string，是被hutool的这个方法内部支持了，其他依赖的就不好说了~
    // string -> object就不行

    @ApiModelProperty("使用状态（1正常 2冻结）")
    private UserStatus status;

    @ApiModelProperty("账户余额")
    private Integer balance;

    @ApiModelProperty("用户地址")
    private List<AddressVO> addresses;
}
