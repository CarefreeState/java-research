package com.macaku.component;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: 马拉圈
 * Date: 2024-01-14
 * Time: 23:36
 */
@SpringBootTest
class EmailSenderTestMessage {

    @Resource
    private EmailSender emailSender;

    @Test
    void sendSimpleMailMessage() {
        System.out.println(emailSender == null);
    }
}