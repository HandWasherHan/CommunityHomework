package com.han.community.controller;

import com.han.community.entity.Comment;
import com.han.community.entity.Post;
import com.han.community.entity.User;
import com.han.community.entity.enums.CommentTargetType;
import com.han.community.entity.enums.LoginMessage;
import com.han.community.service.CommentService;
import com.han.community.service.PostService;
import com.han.community.utils.CheckUserStatusUtils;
import com.han.community.utils.HostHandler;
import com.han.community.utils.OperationValues;
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
        if (!CheckUserStatusUtils.checkByUser(user, response)) {
            return response.toJson();
        }
        comment.setPostId(postId);
        comment.setUserId(user.getId());
        Post postById = postService.getPostById(postId);
        String userId = postById.getUserId();
        comment.setReplyUserId(userId);
        comment.setFloorNum(postById.getFloorCount() + 1);
        comment.setDate(new Date());
        postService.postCommentIncrease(postById, null);
        commentService.save(comment);
        return "success";
    }

    @PostMapping("/add-to-comment/{commentId}")
    public String addCommentToCommentById(@PathVariable String commentId, @RequestBody Comment comment) {
        User user = hostHandler.get();
        if (user == null) {
            return Response.fail(401, LoginMessage.UNAUTHORIZED.getMessage()).toJson();
        }
        comment.setDate(new Date());
        comment.setReplyTargetType(CommentTargetType.COMMENT.getType());
        Comment commentById = commentService.getCommentById(commentId);
        String postId = commentById.getPostId();
        String userId = commentById.getUserId();
        comment.setReplyUserId(userId);
        comment.setReplyCommentId(commentId);
        comment.setPostId(postId);
        comment.setUserId(user.getId());
        comment.setFloorNum(commentById.getFloorNum());
        postService.postCommentIncrease(postService.getPostById(postId));
        commentService.save(comment);
        return Response.success(OperationValues.OPERATION_SUCCESS).toJson();
    }

}
