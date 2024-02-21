package com.itheima.mp.domain.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.itheima.mp.common.UserStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName(value = "user", autoResultMap = true) // 支持结果映射成具体的对象
public class User {

    /**
     * 用户id
     */
    @TableId(value = "id", type = IdType.NONE)
    //如果是input类型，那么也可以实现自增，因为在这里并不会因为id为null而id被省略，所以input的话，就相当于插入id为null的一行
    //这样也算自增，只不过不会自动将自增的值填写到id里
    //设置为auto的话，id为null就表示自增，会被省略，在sql语法中就是自增，并且自增后的值会赋值给这个属性
    //assign_id则是在id为null时自动构造个long型的无规则id，注意：这是在MP的sql构造的时候帮我们设置的“雪花id”，并不是交给mysql生成
    //assign_uuid则是无规则的长字符串
    //默认是none类型，而none类型其实也是默认雪花算法生成id
    private Long id;

    /**
     * 用户名
     */
    @TableField("`username`")
    private String username;

    /**
     * 密码
     */
    @TableField("`password`")
    private String password;

    /**
     * 注册手机号
     */
    @TableField(exist = false) // 设置这个后，查询出来的信息和构造sql，都不会将其与列对应！
    private String phone;

    /**
     * 详细信息
     */
    @TableField(typeHandler = JacksonTypeHandler.class) // 将字符串转化为对象保存到属性里，将对象转化为字符串存到表的字段里
    private UserInfo info;

    /**
     * 使用状态（1正常 2冻结）
     */
    private UserStatus status; // 枚举存在表中的字段仍然叫做status，存储的值为注解@EnumValue的那个字段

    /**
     * 账户余额
     */
    private Integer balance;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
