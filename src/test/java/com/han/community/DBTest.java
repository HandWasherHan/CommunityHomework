package com.han.community;

import com.han.community.entity.MyToken;
import com.han.community.entity.Post;
import com.han.community.entity.User;
import com.han.community.mapper.MyTokenMapper;
import com.han.community.mapper.PostMapper;
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
    PostMapper postMapper;


    @Test
    public void insertTest() {
        User user = new User();
        user.setUsername("hong");
        userMapper.insert(user);
    }

    @Test
    public void ticketTest() {
        Post post = new Post();
        post.setTitle("标题五个字");
        post.setContent("楼主想水帖");
        post.setDate(new Date(System.currentTimeMillis()));
        post.setUserId("10");
        postMapper.insert(post);
    }

    @Test
    public void readNameByPostTest() {
        Post post = postMapper.selectById("1");
        User user = userMapper.selectById(post.getUserId());
        System.err.println(user.getUsername());
    }
}
