package com.han.community.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.han.community.entity.Post;
import com.han.community.mapper.PostMapper;
import com.han.community.service.PostService;
import org.springframework.stereotype.Service;

@Service
public class PostServiceImpl extends ServiceImpl<PostMapper, Post> implements PostService {

    @Override
    public Post getPostById(String id) {
        LambdaQueryWrapper<Post> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Post::getId, id);
        Post one = getOne(lambdaQueryWrapper);
        return one;
    }

    @Override
    public void postCommentIncrease(Post post, Wrapper wrapper) {
        post.setCommentCount(post.getCommentCount() + 1);
        update(post, null);
    }


}
