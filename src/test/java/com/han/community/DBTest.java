package com.han.community;

import com.han.community.entity.MyToken;
import com.han.community.entity.User;
import com.han.community.mapper.MyTokenMapper;
import com.han.community.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
public class DBTest {

    @Autowired
    UserMapper userMapper;

    @Autowired
    MyTokenMapper loginTicketMapper;

    @Test
    public void insertTest() {
        User user = new User();
        user.setUsername("hong");
        userMapper.insert(user);
    }

    @Test
    public void ticketTest() {
        MyToken loginTicket = new MyToken();
        loginTicket.setTicket("hello");
        loginTicket.setStatus(0);
        loginTicket.setUserId("0");
        Date date = new Date(System.currentTimeMillis());
        loginTicket.setExpired(date);
        loginTicketMapper.insert(loginTicket);
        System.err.println(loginTicketMapper.selectById(1));

    }
}
