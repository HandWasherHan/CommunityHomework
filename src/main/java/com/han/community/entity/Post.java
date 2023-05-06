package com.han.community.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.han.community.entity.enums.UserStatus;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class Post {

    @TableId(type = IdType.AUTO)
    private String id;
    private String userId;
    private String title;
    private String content;
    private int status;
//    @DateTimeFormat(pattern="yyyy-MM-dd mm:hh:ss")
//    @JsonFormat(pattern="yyyy-MM-dd mm:hh:ss",timezone="GMT+8")
    private Date date;
    private int commentCount;
    private int floorCount;

    public String toJson() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
