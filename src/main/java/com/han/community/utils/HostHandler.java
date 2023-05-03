package com.han.community.utils;


import com.han.community.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class HostHandler {
    static ThreadLocal<User> threadLocal = new ThreadLocal<User>();

    public User get() {
        return threadLocal.get();
    }

    public void set(User value) {
        threadLocal.set(value);
    }

    public void remove() {
        threadLocal.remove();
    }

    public void clear() {
        threadLocal = new ThreadLocal<>();
    }

    public void add(User user) {
        threadLocal.set(user);
//        log.info("new user set in threadlocal: " + user.getUsername());
//        log.info(threadLocal.get().toString());
//        log.debug(threadLocal.get());

    }
}
