package com.han.community.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class FakeController {

    @PostMapping("/gethotarticletype")
    public String fun1() {
        return "fun1";
    }
    @GetMapping("/getpagearticle")
    public String fun2() {
        return "fun2";
    }
    @GetMapping("/getnew")
    public String fun3() {
        return "fun3";
    }
    @GetMapping("/hotuser")
    public String fun4() {
        return "fun4";
    }

}
