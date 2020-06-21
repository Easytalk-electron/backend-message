package com.example.chatRecord.service;

import com.alibaba.fastjson.JSONObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.websocket.Session;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class MessageService {

    @Autowired
    private OnlineService onlineService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public JSONObject getMessageByIndex(@NotNull String key, long index) {
        return JSONObject.parseObject(stringRedisTemplate.opsForList().index(key, index));
    }

    public List<JSONObject> getMessageByNum(@NotNull String key, long num) {
        long right = getMessageCount(key);
        long left = Long.max(right - num, 0);
        List<JSONObject> result = new ArrayList<>();
        var value = stringRedisTemplate.opsForList().range(key, left, right);
        for (var s : value) {
            result.add(JSONObject.parseObject(s));
        }
        return result;
    }

    public long getMessageCount(@NotNull String key) {
        return stringRedisTemplate.opsForList().size(key);
    }

    public void deleteMessage(@NotNull String key) {
        stringRedisTemplate.delete(key);
    }

    public JSONObject sendMessageToPrivateChat(@NotNull String from, @NotNull String to, @NotNull JSONObject content, @NotNull String time) throws IOException {
        System.out.println(String.format("用户%s向用户%s发送了一条消息，内容为：", from, to));
        var fileId = modifyContent(content);
        var key = generateRedisKeyForPrivateChat(from, to);
        var value = generateRedisValueForMessage(time, from, content);
        var index = saveMessage(key, value);
        var session = onlineService.getSessionById(to);
        if (session != null) {
            var message = new JSONObject(value);
            message.put("index", index);
            message.put("type", "new");
            session.getBasicRemote().sendText(String.valueOf(message));
        }
        return generateResponse(time, index, fileId);
    }

    public JSONObject sendMessageToGroupChat(@NotNull String from, @NotNull String group, @NotNull JSONObject content, @NotNull String time) throws IOException {
        System.out.println(String.format("用户%s向群聊%s发送了一条消息，内容为：", from, group));
        var fileId = modifyContent(content);
        var key = generateRedisKeyForGroupChat(group);
        var value = generateRedisValueForMessage(time, from, content);
        var index = saveMessage(key, value);
        var sessionArray = new Session[2];
        sessionArray[0] = onlineService.getSessionById("1");
        sessionArray[1] = onlineService.getSessionById("2");
        for (var session : sessionArray) {
            if (session != null) {
                var message = new JSONObject(value);
                message.put("index", index);
                message.put("type", "new");
                session.getBasicRemote().sendText(String.valueOf(message));
            }
        }
        return generateResponse(time, index, fileId);
    }

    public String generateRedisKeyForPrivateChat(@NotNull String from, @NotNull String to) {
        if (from.compareTo(to) < 0) {
            return String.format("chat(%s,%s)", from, to);
        }
        return String.format("chat(%s,%s)", to, from);
    }

    public String generateRedisKeyForGroupChat(@NotNull String group) {
        return String.format("group(%s)", group);
    }

    private long saveMessage(@NotNull String key, @NotNull JSONObject value) {
        return stringRedisTemplate.opsForList().rightPush(key, value.toString()) - 1;
    }

    @NotNull
    private JSONObject generateRedisValueForMessage(@NotNull String time, @NotNull String from, @NotNull JSONObject content) {
        var value = new JSONObject();
        value.put("time", time);
        value.put("from", from);
        value.put("content", content);
        return value;
    }

    @NotNull
    private JSONObject generateResponse(@NotNull String time, long index, String fileId) {
        var response = new JSONObject();
        response.put("type", "success");
        response.put("time", time);
        response.put("index", index);
        if (fileId != null) {
            response.put("fileId", fileId);
        }
        return response;
    }

    @Nullable
    private String modifyContent(@NotNull JSONObject content) {
        System.out.println(content);
        if (content.getString("type").equals("file")) {
            var uuid = UUID.randomUUID();
            content.put("uuid", uuid);
            return uuid.toString();
        }
        return null;
    }
}
