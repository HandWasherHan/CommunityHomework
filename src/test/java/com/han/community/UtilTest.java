package com.han.community;


import com.han.community.utils.CommunityStringUtils;
import org.junit.jupiter.api.Test;

public class UtilTest {

    @Test
    public void uuidTest() {
        System.out.println(CommunityStringUtils.generateUUID(8));
    }
}
