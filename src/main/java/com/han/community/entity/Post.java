package com.han.community.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.han.community.entity.enums.UserStatus;
import lombok.Data;

import java.util.Date;

@Data
public class Post {

    @TableId(type = IdType.AUTO)
    private String id;
    private String userId;
    private String title;
    private String content;
    private int status;
    private Date date;
    private int commentCount;

    public String toJson() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
