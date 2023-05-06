package com.han.community;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.han.community.entity.User;
import com.han.community.utils.CommunityStringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class UtilTest {

    @Autowired
    JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    String username;

    @Test
    public void uuidTest() {
        System.out.println(CommunityStringUtils.generateUUID(8));


    }


    @Test
    public void listJsonTest() {
        ObjectMapper objectMapper = new ObjectMapper();
        List<User> list = new ArrayList<>();
        User user = new User();
        user.setId("12");
        user.setUsername("han");
        list.add(user);
        user = new User();
        user.setId("33");
        user.setUsername("ww");
        list.add(user);
//            System.out.println(objectMapper.writeValueAsString(user));
        System.out.println(CommunityStringUtils.getJson(list));
    }

    @Test
    public void mailTest() throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
        mimeMessageHelper.setSubject("~subject~");
        mimeMessageHelper.setText("<p>正文:你好</p>", true);
        System.out.println(username);
        mimeMessageHelper.setFrom(username);
        mimeMessageHelper.setTo("1875981604@qq.com");
        javaMailSender.send(mimeMessage);
    }

}
