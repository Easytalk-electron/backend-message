package com.example.UserManagement.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.UserManagement.bean.Result;
import com.example.UserManagement.entity.User;
import com.example.UserManagement.service.SecurityService;
import com.example.UserManagement.service.UserService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserService userService;

    @PostMapping(value = "/register")
    public Result regist(@NotNull @RequestBody JSONObject request) {
        var data = securityService.decryptDataJSON(request);
        if (data == null) {
            return new Result("data", false, HttpStatus.BAD_REQUEST);
        }

        var password = data.getString("password");
        if (password == null) {
            return new Result("password", false, HttpStatus.BAD_REQUEST);
        }

        var user = new User();
        user.setPassword(password);
        user.setUsername(data.getString("userName"));

        return userService.regist(user);
    }

    @PostMapping(value = "/login")
    public Result login(@NotNull @RequestBody JSONObject request) {
        var data = securityService.decryptDataJSON(request);
        if (data == null) {
            return new Result("data", false, HttpStatus.BAD_REQUEST);
        }

        var uid = data.getString("id");
        if (uid == null) {
            return new Result("id", false, HttpStatus.BAD_REQUEST);
        }

        var password = data.getString("password");
        if (password == null) {
            return new Result("password", false, HttpStatus.BAD_REQUEST);
        }

        var user = new User();
        user.setId(Integer.parseInt(uid));
        user.setPassword(password);

        return userService.login(user);

    }
}
