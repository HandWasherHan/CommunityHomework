package com.han.community.entity.enums;

public enum CommentOrPostStatus {
    NORMAL(0),
    BLOCKED(1),
    DELETED(2),
    RECOMMENDED(3);

    private int status;

    CommentOrPostStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}
