package com.han.community.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Component
public class MyMailSender {
    static final String DEFAULT_SUBJECT = "验证码";
    static final String PRE_TEMPLATE_MESSAGE = "您的验证码为：</br>";
    static final String AFTER_TEMPLATE_MESSAGE = "</br>请妥善使用！";
    static final String B_START = "<b>";
    static final String B_END = "</b>";

    @Autowired
    JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    String mailUsername;


    public boolean sendMail(String text, String subject, String to) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
        try {
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(text, true);
//            System.out.println(mailUsername);
            mimeMessageHelper.setFrom(mailUsername);
            mimeMessageHelper.setTo(to);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean sendVerificationCodeMail(String code, String to) {
        code = B_START + code + B_END;
        return sendMail(PRE_TEMPLATE_MESSAGE + code + AFTER_TEMPLATE_MESSAGE, DEFAULT_SUBJECT, to);
    }
}
