package com.example.chatRecord.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.chatRecord.service.MessageService;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MessageController {

    @Autowired
    private MessageService messageService;

    @GetMapping("/getPrivateMessageByIndex")
    JSONObject getPrivateMessageByIndex(@NotNull String from, @NotNull String to, long index) {
        var key = messageService.generateRedisKeyForPrivateChat(from, to);
        return messageService.getMessageByIndex(key, index);
    }

    @GetMapping("/getGroupMessageByIndex")
    JSONObject getGroupMessageByIndex(@NotNull String group, long index) {
        var key = messageService.generateRedisKeyForGroupChat(group);
        return messageService.getMessageByIndex(key, index);
    }

    @GetMapping("/getPrivateMessageByNum")
    List<JSONObject> getPrivateMessageByNum(@NotNull String from, @NotNull String to, long num, @Nullable Long end) {
        var key = messageService.generateRedisKeyForPrivateChat(from, to);
        return messageService.getMessageByNum(key, num, end);
    }

    @GetMapping("/getGroupMessageByNum")
    List<JSONObject> getGroupMessageByNum(@NotNull String group, long num, @Nullable Long end) {
        var key = messageService.generateRedisKeyForGroupChat(group);
        return messageService.getMessageByNum(key, num, end);
    }

    @GetMapping("/getPrivateMessageCount")
    long getPrivateMessageCount(@NotNull String from, @NotNull String to) {
        var key = messageService.generateRedisKeyForPrivateChat(from, to);
        return messageService.getMessageCount(key);
    }

    @GetMapping("/getGroupMessageCount")
    long getGroupMessageCount(@NotNull String group) {
        var key = messageService.generateRedisKeyForGroupChat(group);
        return messageService.getMessageCount(key);
    }

    @DeleteMapping("/deletePrivateMessage")
    void deletePrivateMessage(@NotNull String from, @NotNull String to) {
        var key = messageService.generateRedisKeyForPrivateChat(from, to);
        messageService.deleteMessage(key);
    }

    @DeleteMapping("/deleteGroupMessage")
    void deleteGroupMessage(@NotNull String group) {
        var key = messageService.generateRedisKeyForGroupChat(group);
        messageService.deleteMessage(key);
    }
}
