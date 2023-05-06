package com.han.community.utils;

import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.han.community.entity.User;
import org.springframework.util.DigestUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CommunityStringUtils {
    public static String generateUUID(int length) {
        return UUID.randomUUID().toString().substring(0, length);
    }

    public static String md5Digest(String key) {
        return DigestUtils.md5DigestAsHex(key.getBytes());
    }

    public static String getJson(Object object) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        String json;
        try {
            json = objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return json;
    }
}
