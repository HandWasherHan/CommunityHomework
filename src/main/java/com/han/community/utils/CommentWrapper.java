package com.han.community.utils;

import com.han.community.entity.Comment;
import com.han.community.entity.User;
import lombok.Data;

import java.util.List;

@Data
public class CommentWrapper {
    private Comment comment;
    private User Publisher;
    private List<CommentWrapper> replyList;

    public CommentWrapper(Comment comment, List<CommentWrapper> replyList) {
        this.comment = comment;
        this.replyList = replyList;
    }

    public CommentWrapper(Comment comment) {
        this.comment = comment;
    }

    public CommentWrapper(Comment comment, User publisher, List<CommentWrapper> replyList) {
        this.comment = comment;
        Publisher = publisher;
        this.replyList = replyList;
    }
}
