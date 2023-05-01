package com.han.community.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.han.community.entity.Post;
import com.han.community.mapper.PostMapper;
import com.han.community.service.PostService;
import org.springframework.stereotype.Service;

@Service
public class PostServiceImpl extends ServiceImpl<PostMapper, Post> implements PostService {

}
