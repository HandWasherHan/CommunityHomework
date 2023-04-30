package com.han.community.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

import java.util.Date;

@Data
public class MyToken {

    @TableId(type = IdType.AUTO)
    private String id;

    private String userId;
    private String content;
    private int status;
    private Date expired;

    public String toJson() {

        ObjectMapper objectMapper = new ObjectMapper();
        String str = null;
        try {
            str = objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return str;
    }
}
