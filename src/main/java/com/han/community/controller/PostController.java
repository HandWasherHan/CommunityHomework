package com.han.community.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.han.community.entity.Post;
import com.han.community.entity.User;
import com.han.community.mapper.PostMapper;
import com.han.community.service.PostService;
import com.han.community.service.UserService;
import com.han.community.utils.CommunityStringUtils;
import com.han.community.utils.HostHandler;
import com.han.community.utils.Response;
import com.han.community.utils.UserStatus;
import javafx.geometry.Pos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/post")
public class PostController {
    static final int PAGE_SIZE = 3;



    @Autowired
    PostService postService;

    @Autowired
    PostMapper postMapper;

    @Autowired
    HostHandler hostHandler;

    @Autowired
    UserService userService;

    @PostMapping("/add")
    public String addPost(@RequestBody Post post) {
        post.setDate(new Date());
        User user = hostHandler.get();
        if (user == null) {
//            Response response;
            return Response.fail(403, "未登录，无权发帖").toJson();
        }
        String id = user.getId();
        if (user.getStatus() != UserStatus.NORMAL.getCode()) {
            return Response.fail(403, "账号异常").toJson();
        }
        post.setTitle(HtmlUtils.htmlEscape(post.getTitle()));
        post.setContent(HtmlUtils.htmlEscape(post.getContent()));
        post.setUserId(id);
        postService.save(post);
        return Response.success("success").toJson();
    }

    @PostMapping("/get")
    public String getPost(@RequestParam(name = "current") int current) {
//        postService.get
//        int current = user.getStatus();
        IPage page = new Page(current, PAGE_SIZE);
        LambdaQueryWrapper<Post> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        postMapper.selectPage(page, lambdaQueryWrapper);
//        lambdaQueryWrapper
        List list = page.getRecords();
        Response<Map> response = new Response();
        response.setStatusCode(200);
        response.setEntity(new ConcurrentHashMap<String, Object>());
        response.getEntity().put("total pages", page.getTotal());
        response.getEntity().put("list", list);
        return CommunityStringUtils.getJson(response);
    }

    @GetMapping("/get/{postId}")
    public String getPostDetailById(@PathVariable String postId) {
        LambdaQueryWrapper<Post> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Post::getId, postId);
        Post one = postService.getOne(lambdaQueryWrapper);
        Response<Map> response = new Response<>();
        LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userLambdaQueryWrapper.eq(User::getId, one.getUserId());
        User userOne = userService.getOne(userLambdaQueryWrapper);
        response.setEntity(new ConcurrentHashMap<String, Object>());
        if (!(userOne == null)){
            response.getEntity().put("user name", userOne.getUsername());
        } else {
            response.getEntity().put("user name", "未知用户");
        }

        response.getEntity().put("post", one);
        return CommunityStringUtils.getJson(response.getEntity());
    }

    @PostMapping("/comment/{postId}")
    public String addCommentToPostByPostId(@PathVariable(name = "postId") String postId) {
        Post post = postService.getPostById(postId);
        post.setCommentCount(post.getCommentCount() + 1);
        return null;
    }


}
