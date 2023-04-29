package com.han.community.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.han.community.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
