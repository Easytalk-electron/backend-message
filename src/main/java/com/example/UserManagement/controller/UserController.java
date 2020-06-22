package com.example.UserManagement.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.UserManagement.bean.Result;
import com.example.UserManagement.entity.Login;
import com.example.UserManagement.entity.User;
import com.example.UserManagement.service.SecurityService;
import com.example.UserManagement.service.UserService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserService userService;

    @PostMapping(value = "/register")
    public JSONObject regist(@RequestBody Login login) {
            var username1 = login.getUsername();
            var password1 = login.getPassword();
//        if (request == null) {
//            Result result = new Result("null request", false, HttpStatus.BAD_REQUEST);
//            JSONObject jo = JSONObject.parseObject(JSONObject.toJSONString(result));
//            return jo;
//        }

        var username = username1;
        if (username == null) {
            Result result = new Result("null username", false, HttpStatus.BAD_REQUEST);
            JSONObject jo = JSONObject.parseObject(JSONObject.toJSONString(result));
            return jo;
        }

        var password = password1;
        if (password == null) {
            Result result = new Result("null password", false, HttpStatus.BAD_REQUEST);
            JSONObject jo = JSONObject.parseObject(JSONObject.toJSONString(result));
            return jo;
        }

        var user = new User();
        user.setPassword(password);
        user.setUsername(username);

        Result result = userService.regist(user);
        JSONObject jo = JSONObject.parseObject(JSONObject.toJSONString(result));
        return jo;
    }

    @PostMapping(value = "/login")
    public JSONObject login(@RequestBody Login login) {
        var id1 = login.getId();
        var password1 = login.getPassword();
//        var data = securityService.decryptDataJSON(request);
//        JSONObject request = JSONObject.parseObject(request1);
//        if (request == null) {
//            Result result = new Result("null request", false, HttpStatus.BAD_REQUEST);
//            JSONObject jo = JSONObject.parseObject(JSONObject.toJSONString(result));
//            return jo;
//        }

        var uid = id1;
        if (uid == null) {
            Result result = new Result("null id", false, HttpStatus.BAD_REQUEST);
            JSONObject jo = JSONObject.parseObject(JSONObject.toJSONString(result));
            return jo;
        }

        var password = password1;
        if (password == null) {
            Result result = new Result("null password", false, HttpStatus.BAD_REQUEST);
            JSONObject jo = JSONObject.parseObject(JSONObject.toJSONString(result));
            return jo;
        }

        var user = new User();
        user.setId(Integer.parseInt(uid));
        user.setPassword(password);
        Result result = userService.login(user);
        JSONObject jo = JSONObject.parseObject(JSONObject.toJSONString(result));
        return jo;

    }
}
