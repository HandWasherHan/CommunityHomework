package com.han.community.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.han.community.entity.MyToken;
import com.han.community.mapper.MyTokenMapper;
import com.han.community.service.MyTokenService;
import org.springframework.stereotype.Service;

@Service
public class MyTokenServiceImpl extends ServiceImpl<MyTokenMapper, MyToken> implements MyTokenService {
}
