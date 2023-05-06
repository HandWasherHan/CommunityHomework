package com.han.community.controller;

import com.han.community.CommonValues;
import com.han.community.entity.User;
import com.han.community.service.UserService;
import com.han.community.utils.CommunityStringUtils;
import com.han.community.utils.MyMailSender;
import com.han.community.utils.Response;
import com.han.community.utils.UserValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user-sign")
public class SignController implements UserValue {

    @Autowired
    UserService userService;

    @Autowired
    MyMailSender mailSender;

    @Autowired
    RedisTemplate redisTemplate;

    @PostMapping("/code")
    public String doVerificationCode(@RequestBody User user) {
        if (user == null || user.getMail() == null || user.getPassword() == null || user.getUsername() == null) {
            return Response.fail(400, NO_ENOUGH_INFO).toJson();
        }
        String verificationCode = CommunityStringUtils.generateUUID(4);
        redisTemplate.opsForValue().set(user.getMail(), verificationCode);
        if (mailSender.sendVerificationCodeMail(verificationCode, user.getMail())) {
            return Response.success(SUCCESS_VERIFICATION_CODE_SEND).toJson();
        } else {
            return Response.fail(400, UNKNOWN_FAILURE).toJson();
        }
    }

    @PostMapping("/sign")
    public String doSign(@RequestBody User user, @PathVariable(required = false) boolean rememberMe, @RequestParam(name = "code", required = false) String code) {

        if (!code.equals(redisTemplate.opsForValue().get(user.getMail()))) {
            System.err.println(code);
            System.err.println(redisTemplate.opsForValue().get(user.getMail()));
            return Response.fail(400, FAILURE_VERIFICATION).toJson();
        }
        User one = userService.getUserByName(user.getUsername());
        if (one != null) {
            return Response.fail(REPEAT_NAME_SIGN).toJson();
        }
        String salt = CommunityStringUtils.generateUUID(CommonValues.DEFAULT_SALT_LENGTH);
        String password = CommunityStringUtils.md5Digest(
                CommunityStringUtils.md5Digest(user.getPassword()) + salt);
        user.setSalt(salt);
        user.setPassword(password);
        userService.save(user);
        // TODO 将token存到redis里，以实现分布式一致性、并发幂等性，目前仅存入mysql中
        String jwtToken = userService.loginService(user, rememberMe);

        Response<String> response = Response.success(SUCCESS_SIGN);
        response.setEntity(jwtToken);
        return response.toJson();


    }




    @PostMapping("/sign/admin")
    public String doSignAdmin(@RequestBody User user, @PathVariable(required = false) boolean rememberMe) {

        user.setStatus(3);
        return doSign(user, rememberMe, null);

    }
}
