package com.macaku.controller;

import com.macaku.component.po.EmailMessage;
import com.macaku.domain.dto.EmailBatch;
import com.macaku.domain.dto.ModelMessage;
import com.macaku.service.EmailService;
import com.macaku.utils.MediaUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: 马拉圈
 * Date: 2024-01-14
 * Time: 19:10
 */
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/email")
@Api(tags = "邮箱相关接口")
public class EmailSendController {

    private final EmailService emailService;

    @PostMapping("/send")
    @ApiOperation("发送普通消息")
    public void sendEmail(EmailBatch emailBatch) {
        EmailMessage emailMessage = emailBatch.transfer();
        emailService.sendSimpleMailMessage(emailMessage);
        log.info("发送成功");
    }

    @PostMapping("/attachment")
    @ApiOperation("发送带附件消息")
    public void sendEmailWithAttachment(EmailBatch emailBatch,
                                        @ApiParam("附件") @NonNull @RequestPart("attachment") List<MultipartFile> multipartFiles) {
        File[] attachments = MediaUtils.getFilesFilterImage(multipartFiles);
        // 发送邮件
        EmailMessage emailMessage = emailBatch.transfer();
        emailService.sendMailWithFile(emailMessage, attachments);
//        attachment.delete();//删除附件在本地的保存
        log.info("发送成功");
    }

    @PostMapping("/model")
    @ApiOperation("发送模板消息")
    public void sendModelMail(EmailBatch emailBatch) {
        EmailMessage emailMessage = emailBatch.transfer();
        // 构造模板消息
        ModelMessage modelMessage = new ModelMessage();
        modelMessage.setTitle(emailMessage.getTitle());
        modelMessage.setContent(emailMessage.getContent());
        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(emailMessage.getCreateTime());
        modelMessage.setCreateTime(date);
        modelMessage.setSenderName(emailService.selectNameByEmail(emailMessage.getSender()));
        modelMessage.setRecipientName(emailService.selectNameByEmail(emailMessage.getRecipient(0)));
        // 发送模板消息
        emailService.sendModelMail(emailMessage, modelMessage);
        log.info("发送成功");
    }

    @PostMapping("/attachment/model")
    @ApiOperation("发送模板消息携带附件")
    public void sendModelMailWithFile(EmailBatch emailBatch,
                                      @ApiParam("附件") @NonNull @RequestPart("attachment") List<MultipartFile> multipartFiles) {
        EmailMessage emailMessage = emailBatch.transfer();
        File[] attachments = MediaUtils.getFiles(multipartFiles);
        // 构造模板消息
        ModelMessage modelMessage = new ModelMessage();
        modelMessage.setTitle(emailMessage.getTitle());
        modelMessage.setContent(emailMessage.getContent());
        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(emailMessage.getCreateTime());
        modelMessage.setCreateTime(date);
        modelMessage.setSenderName(emailService.selectNameByEmail(emailMessage.getSender()));
        modelMessage.setRecipientName(emailService.selectNameByEmail(emailMessage.getRecipient(0)));
        // 发送模板消息
        emailService.sendModelMail(emailMessage, modelMessage, attachments);
        log.info("发送成功");

    }


        @PostMapping("/mass")
    @ApiOperation("群发模板消息(逐个定制)")
    public void massEmail(EmailBatch emailBatch,
                          @ApiParam("附件") @NonNull @RequestPart("attachment") List<MultipartFile> multipartFiles) {
        EmailMessage emailMessage = emailBatch.transfer();
        File[] attachments = MediaUtils.getFiles(multipartFiles);
        // 发送模板消息
        emailService.customizedSendEmail(emailMessage, "ModelMessage.html", s -> {
            // 构造模板消息
            ModelMessage modelMessage = new ModelMessage();
            modelMessage.setTitle(emailMessage.getTitle());
            modelMessage.setContent(emailMessage.getContent());
            String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(emailMessage.getCreateTime());
            modelMessage.setCreateTime(date);
            modelMessage.setSenderName(emailService.selectNameByEmail(emailMessage.getSender()));
            String recipientName = emailService.selectNameByEmail((String) s);
            modelMessage.setRecipientName(recipientName);
            return modelMessage;
        }, attachments);
        log.info("发送成功");
    }



}
