package com.han.community.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.han.community.entity.User;
import com.han.community.mapper.UserMapper;
import com.han.community.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
