package com.itheima.mp.domain.query;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Arrays;

@Data
@ApiModel(description = "用户查询条件实体")
public class PageQuery {

    @ApiModelProperty("页码")
    private Integer pageNo = 1;
    @ApiModelProperty("页内条数")
    private Integer pageSize = 5;
    @ApiModelProperty("根据什么排序")
    private String sortBy;
    @ApiModelProperty("是否正序")
    private Boolean isAsc;

    public <T>  Page<T> toMpPage(OrderItem ... orders){
        // 1.分页条件
        Page<T> p = Page.of(pageNo, pageSize);
        // 2.排序条件
        // 2.1.先看前端有没有传排序字段
        if (sortBy != null) {
            p.addOrder(new OrderItem(sortBy, isAsc));
            return p;
        }
        // 2.2.再看有没有手动指定排序字段
        if(CollUtil.isEmpty(Arrays.asList(orders))) {
            p.addOrder(new OrderItem("update_time", false));//如果干脆默认排序都不传了，就这样
            return p;
        }
        p.addOrder(orders);
        return p;
    }

    public <T> Page<T> toMpPage(String defaultSortBy, boolean isAsc){
        return this.toMpPage(new OrderItem(defaultSortBy, isAsc));
    }

    public <T> Page<T> toMpPageDefaultSortByCreateTimeDesc() {
        return toMpPage("create_time", false);
    }

    public <T> Page<T> toMpPageDefaultSortByUpdateTimeDesc() {
        return toMpPage("update_time", false);
    }
}
