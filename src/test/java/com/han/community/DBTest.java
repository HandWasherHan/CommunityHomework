package com.han.community;

import com.han.community.entity.User;
import com.han.community.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
}
