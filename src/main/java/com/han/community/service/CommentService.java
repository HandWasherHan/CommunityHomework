package com.han.community.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.han.community.entity.Comment;

import java.util.List;


public interface CommentService extends IService<Comment> {

    List getCommentsByPostId(String postId);
}
