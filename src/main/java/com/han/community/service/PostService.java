package com.han.community.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.han.community.entity.Post;

public interface PostService extends IService<Post> {

    Post getPostById(String id);

    void postCommentIncrease(Post post, Wrapper wrapper);


}
