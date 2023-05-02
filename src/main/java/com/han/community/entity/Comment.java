package com.han.community.entity;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

@Data
@Slf4j
public class Comment {
    private String id;
    private String userId;
    private String postId;
    private String content;
    private int type;
    private int status;
    private String replyUserId;
    private Date date;
}
