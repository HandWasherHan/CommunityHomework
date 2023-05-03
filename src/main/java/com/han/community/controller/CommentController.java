package com.han.community.controller;

import com.han.community.entity.Comment;
import com.han.community.entity.Post;
import com.han.community.entity.User;
import com.han.community.service.CommentService;
import com.han.community.service.PostService;
import com.han.community.utils.CheckUserStatusUtils;
import com.han.community.utils.HostHandler;
import com.han.community.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Date;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    CommentService commentService;

    @Autowired
    PostService postService;

    @Autowired
    HostHandler hostHandler;

    @PostMapping("/add/{postId}")
    public String addCommentToPostByPostId(@PathVariable("postId") String postId, @RequestBody Comment comment) {
        User user = hostHandler.get();
        Response<Object> response = new Response<>();
        response.setMessage("ha");
        if (!CheckUserStatusUtils.checkByUser(user, response)) {
            return response.toJson();
        }
        comment.setPostId(postId);
        comment.setUserId(user.getId());
        Post postById = postService.getPostById(postId);
        String userId = postById.getUserId();
        comment.setReplyUserId(userId);
        comment.setDate(new Date());
        postService.postCommentIncrease(postById, null);
        commentService.save(comment);
        return "success";
    }
}
