package com.example.UserManagement.controller;

import com.alibaba.fastjson.JSONObject;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FriendListController {
    @RequestMapping("/friendList")
    public JSONObject getFriendList(@NotNull JSONObject request){
        String id = request.getString("username");

    }
}
