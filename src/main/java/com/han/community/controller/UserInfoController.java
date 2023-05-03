package com.han.community.controller;

import com.han.community.entity.User;
import com.han.community.entity.enums.LoginMessage;
import com.han.community.entity.enums.UserInfoMessageEnum;
import com.han.community.entity.enums.UserStatus;
import com.han.community.service.UserService;
import com.han.community.utils.CommunityStringUtils;
import com.han.community.utils.HostHandler;
import com.han.community.utils.OperationValues;
import com.han.community.utils.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.net.ssl.HostnameVerifier;
import javax.servlet.http.HttpSession;

@Slf4j
@RestController
@RequestMapping("/user/info")
public class UserInfoController implements OperationValues {

    @Autowired
    UserService userService;

    @Autowired
    HostHandler hostHandler;

    @PostMapping("/alter")
    public String alterInfo(@RequestBody User user) {
        User userInTL = hostHandler.get();
        if (userInTL == null) {
            return Response.fail(401, UserInfoMessageEnum.UNKNOWN_ERROR.getMessage()).toJson();
        }
        User userInDb = userService.getUserById(userInTL.getId());

        if (!user.getUsername().equals(userInTL.getUsername()) && userService.getUserByName(user.getUsername()) != null) {
            return Response.fail(400, UserInfoMessageEnum.REPEAT_NAME.getMessage()).toJson();
        }
        userInDb.setPassword(CommunityStringUtils.md5Digest(
                CommunityStringUtils.md5Digest(user.getPassword())
                        + (userInDb.getSalt() == null ? "" : userInDb.getSalt())));
        userInDb.setUsername(user.getUsername());
        userService.alterUserById(userInDb.getId(), userInDb);
        return Response.success(UserInfoMessageEnum.ALTER_SUCCESS.getMessage()).toJson();
    }

    @PostMapping("/ban/{userId}")
    public String banUserById(@PathVariable String userId) {
        User userById = userService.getUserById(userId);
        userById.setStatus(UserStatus.SUSPENDED.getCode());
        userService.alterUserById(userId, userById);
        return Response.success(OPERATION_SUCCESS).toJson();
    }

    @PostMapping("/delete/{userId}")
    public String deleteUserById(@PathVariable String userId) {
        User userById = userService.getUserById(userId);
        userById.setStatus(UserStatus.DELETED.getCode());
        userService.alterUserById(userId, userById);
        return Response.success(OPERATION_SUCCESS).toJson();
    }
}
