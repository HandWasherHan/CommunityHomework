package com.han.community.controller;

import com.han.community.entity.Post;
import com.han.community.entity.User;
import com.han.community.service.PostService;
import com.han.community.utils.HostHandler;
import com.han.community.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/post")
public class PostController {

    @Autowired
    PostService postService;

    @Autowired
    HostHandler hostHandler;

    @PostMapping("/add")
    public String addPost(@RequestBody Post post) {
        post.setDate(new Date());
        User user = hostHandler.get();
        if (user == null) {
//            Response response;
            return Response.fail("unlogin").toJson();
        }
        String id = user.getId();
        post.setUserId(id);
        postService.save(post);
        return Response.success("success").toJson();
    }

}
