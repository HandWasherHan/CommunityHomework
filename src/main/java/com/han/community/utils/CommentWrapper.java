package com.han.community.utils;

import com.han.community.entity.Comment;
import lombok.Data;

import java.util.List;

@Data
public class CommentWrapper {
    private Comment comment;
    private List<Comment> replyList;

    public CommentWrapper(Comment comment, List<Comment> replyList) {
        this.comment = comment;
        this.replyList = replyList;
    }
}
