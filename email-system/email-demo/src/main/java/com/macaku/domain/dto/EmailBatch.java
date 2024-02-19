package com.macaku.domain.dto;

import cn.hutool.core.bean.BeanUtil;
import com.macaku.component.po.EmailMessage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: 马拉圈
 * Date: 2024-01-15
 * Time: 1:45
 */
@Data
@ApiModel(description = "邮件实体")
public class EmailBatch implements Serializable {


    @ApiModelProperty("收件人")
    private String[] recipient;

    @ApiModelProperty("发件人")
    private String sender;

    @ApiModelProperty("抄送人")
    private String[] carbonCopy;

    @ApiModelProperty("标题")
    private String title;

    @ApiModelProperty("正文")
    private String content;

    public EmailMessage transfer() {
        EmailMessage emailMessage = BeanUtil.copyProperties(this, EmailMessage.class);
        emailMessage.setRecipient(this.getRecipient());
        emailMessage.setCarbonCopy(this.getCarbonCopy());
        emailMessage.setCreateTime(new Date());
        return emailMessage;
    }


    private static final long serialVersionUID = 1L;

}
