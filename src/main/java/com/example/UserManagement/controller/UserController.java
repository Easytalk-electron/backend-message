package com.example.UserManagement.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.UserManagement.bean.Result;
import com.example.UserManagement.entity.User;
import com.example.UserManagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")

public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping(value = "/register")
    public Result regist(JSONObject user) {

        return userService.regist(user);
    }

    @PostMapping(value = "/login")
    public Result login(User user) {
        return userService.login(user);

    }
}
