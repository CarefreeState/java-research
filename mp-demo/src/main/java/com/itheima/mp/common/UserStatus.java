package com.itheima.mp.common;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: 马拉圈
 * Date: 2023-12-10
 * Time: 12:17
 */
@Getter
@AllArgsConstructor
public enum UserStatus {
    NORMAL(1, "正常"),
    FROZEN(2, "冻结"),
    ;
    @EnumValue//表示这个属性作为枚举存在数据库表中的字段
    private final int value;
    @JsonValue//返回是返回desc的值作为枚举的值，前端传参枚举则还是以传递枚举的具体值即可：NORMAL、FROZEN...
    private final String desc;
}
