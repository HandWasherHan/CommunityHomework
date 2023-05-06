package com.han.community.entity.enums;

public enum CommentTargetType {
    POST(0),
    COMMENT(1);


    private int type;

    CommentTargetType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }
}
