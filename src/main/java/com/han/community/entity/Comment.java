package com.han.community.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

@Data
@Slf4j
public class Comment {
    @TableId(type = IdType.AUTO)
    private String id;
    private String userId;
    private String postId;
    private String content;
    private String replyCommentId;
    private int type;
    private int status;
    private String replyUserId;
    private Date date;
    private int replyTargetType;
    private int floorNum;
}
