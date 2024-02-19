package com.macaku.service;

import com.macaku.component.po.EmailMessage;
import com.macaku.domain.dto.ModelMessage;

import java.io.File;
import java.util.function.Function;

/**
* @author 马拉圈
* @description 针对表【email】的数据库操作Service
* @createDate 2024-01-14 18:48:23
*/
public interface EmailService {


    void sendSimpleMailMessage(EmailMessage emailMessage);


    void sendMailWithFile(EmailMessage emailMessage, File... files);


    void sendModelMail(EmailMessage emailMessage, ModelMessage modelMessage);

    String selectNameByEmail(String email);

    <T, R> void customizedSendEmail(EmailMessage emailMessage, String template, Function<T, R> function, File... file);

    void sendModelMail(EmailMessage emailMessage, ModelMessage modelMessage, File... attachment);
}
