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


    @Test
    public void insertTest() {
        User user = new User();
        user.setUsername("hong");
        userMapper.insert(user);
    }

    @Test
    public void ticketTest() {

    }
}
