package com.wenllar.controller;

import com.wenllar.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping("/test")
    public String test(String name){
        System.out.println("接受到test请求");
        userService.test(name);
        return "Hello: " + name;
    }
}
