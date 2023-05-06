package com.han.community.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.han.community.entity.Comment;
import com.han.community.mapper.CommentMapper;
import com.han.community.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {
    @Autowired
    CommentMapper commentMapper;

    @Override
    public List getCommentsByPostId(String postId) {
        LambdaQueryWrapper<Comment> listLambdaQueryWrapper = new LambdaQueryWrapper<>();
//        listLambdaQueryWrapper.
        listLambdaQueryWrapper.eq(Comment::getPostId, postId);
        List<Comment> comments = commentMapper.selectList(listLambdaQueryWrapper);
        return comments;
    }

    @Override
    public Comment getCommentById(String id) {
        LambdaQueryWrapper<Comment> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Comment::getId, id);
        Comment comment = getOne(lambdaQueryWrapper);
        return comment;
    }
}
