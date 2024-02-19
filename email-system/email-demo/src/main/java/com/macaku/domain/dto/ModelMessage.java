package com.macaku.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "模板消息")
public class ModelMessage {

    @ApiModelProperty("标题")
    private String title;

    @ApiModelProperty("收件人姓名")
    private String recipientName;

    @ApiModelProperty("发件人姓名")
    private String senderName;

    @ApiModelProperty("内容")
    private String content;

    @ApiModelProperty("创建时间")
    private String createTime;

}