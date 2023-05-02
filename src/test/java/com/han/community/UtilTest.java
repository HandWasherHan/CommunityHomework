package com.han.community;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.han.community.entity.User;
import com.han.community.utils.CommunityStringUtils;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class UtilTest {

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
}
