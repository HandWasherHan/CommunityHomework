package com.han.community.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.han.community.entity.Comment;
import com.han.community.entity.Post;
import com.han.community.entity.User;
import com.han.community.entity.enums.CommentTargetType;
import com.han.community.mapper.PostMapper;
import com.han.community.service.CommentService;
import com.han.community.service.PostService;
import com.han.community.service.UserService;
import com.han.community.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import java.util.ArrayList;
import java.util.Date;
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

    @Autowired
    CommentService commentService;

    @PostMapping("/add")
    public String addPost(@RequestBody Post post) {
        post.setDate(new Date());
        User user = hostHandler.get();
        if (user == null) {
            return Response.fail(403, "未登录，无权发帖").toJson();
        }
        String id = user.getId();
        Response response = new Response<>();
        if (!CheckUserStatusUtils.checkByUser(user, response)) {
            return response.toJson();
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
        Post one = postService.getPostById(postId);
        Response<Map> response = new Response<>();
        User userOne = userService.getUserById(one.getUserId());
        response.setEntity(new ConcurrentHashMap<String, Object>());
        response.getEntity().put("post", one);
        List<Comment> commentByPostId = commentService.getCommentsByPostId(postId);
        Map<String, CommentWrapper> commentListMap = new ConcurrentHashMap<>();
        for (Comment comment:
             commentByPostId) {
            if (comment.getReplyTargetType() == CommentTargetType.POST.getType()) {
                System.err.println(comment.getContent());
                List<Comment> commentToPostList = new ArrayList<>();
                CommentWrapper commentWrapper = new CommentWrapper(comment, new ArrayList<Comment>());
                commentListMap.put(comment.getId(), commentWrapper);
            }
        }
        for (Comment comment:
             commentByPostId) {
            if (comment.getReplyTargetType() == CommentTargetType.COMMENT.getType()) {
                commentListMap.get(comment.getReplyCommentId())
                        .getReplyList()
                        .add(comment);
            }
        }
        response.getEntity().put("comments", commentListMap);
        if (!(userOne == null)){
            response.getEntity().put("user name", userOne.getUsername());
        } else {
            response.getEntity().put("user name", "未知用户");
        }
        return CommunityStringUtils.getJson(response);
    }


    @PostMapping("/comment/{postId}")
    public String addCommentToPostByPostId(@PathVariable(name = "postId") String postId) {
        Post post = postService.getPostById(postId);
        post.setCommentCount(post.getCommentCount() + 1);
        return null;
    }


}
