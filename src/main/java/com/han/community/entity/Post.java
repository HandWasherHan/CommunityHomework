package com.han.community.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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

}
