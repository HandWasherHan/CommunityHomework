package com.han.community.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class User {

    @TableId(type = IdType.AUTO)
    private String id;
    private String username;
    private String password;
    private int status = 0;
    private String salt;
}
