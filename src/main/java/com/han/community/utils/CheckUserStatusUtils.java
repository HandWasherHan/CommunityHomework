package com.han.community.utils;

import com.han.community.entity.User;
import com.han.community.entity.enums.LoginMessage;
import com.han.community.entity.enums.UserStatus;

public class CheckUserStatusUtils {
    static final int SUSPENDED_CODE = 1;
    static final int DELETED_CODE = 2;
    public static boolean checkByUser(User user, Response response) {
        if (user == null) {
//            response.get
            response.setMessage(LoginMessage.UNAUTHORIZED.getMessage());
            response.setStatusCode(401);
            return false;
        }
        int status = user.getStatus();
        switch (status) {
            case SUSPENDED_CODE: {
                response.setStatusCode(403);
                response.setMessage(LoginMessage.SUSPENDED_USER.getMessage());
                return false;
            }
            case DELETED_CODE: {
                response.setStatusCode(403);
                response.setMessage(LoginMessage.DELETED.getMessage());
                return false;
            }
            default: break;
        }
        return true;
    }
}
