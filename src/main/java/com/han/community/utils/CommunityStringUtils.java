package com.han.community.utils;

import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.springframework.util.DigestUtils;

import java.util.UUID;

public class CommunityStringUtils {
    public static String generateUUID(int length) {
        return UUID.randomUUID().toString().substring(0, length);
    }

    public static String md5Digest(String key) {
        return DigestUtils.md5DigestAsHex(key.getBytes());
    }
}
