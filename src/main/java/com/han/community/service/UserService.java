package com.han.community.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.han.community.entity.Comment;
import com.han.community.entity.User;

import java.util.List;
import java.util.Map;

public interface UserService extends IService<User> {


    String loginService(User user,  boolean rememberMe);

    User getUserById(String id);

    User getUserByName(String name);

    void alterUserById(String id, User user);

    Map<String, User> readUserInCommentList(List<Comment> commentList);
}
