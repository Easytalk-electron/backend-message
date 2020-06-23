package com.example.chatRecord.websocket;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.example.chatRecord.service.MessageService;
import com.example.chatRecord.service.OnlineService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

@Component
@ServerEndpoint("/online")
public class OnlineWebSocket {

    private static MessageService messageService;
    private static OnlineService onlineService;

    @Autowired
    public void setOnlineService(MessageService messageService) {
        OnlineWebSocket.messageService = messageService;
    }

    @Autowired
    public void setOnlineService(OnlineService onlineService) {
        OnlineWebSocket.onlineService = onlineService;
    }

    @OnOpen
    public void onOpen(@NotNull Session session) throws IOException {
        System.out.println(session);
        var requestParameterMap = session.getRequestParameterMap();
        var idList = requestParameterMap.get("id");
        if (idList != null && idList.size() == 1) {
            var id = idList.get(0);
            onlineService.online(id, session);
            var response = new JSONObject();
            response.put("type", "success");
            response.put("time", String.valueOf(new java.sql.Timestamp(System.currentTimeMillis())));
            session.getBasicRemote().sendText(response.toString());
        }
        else {
            sendJsonErrorText(session, "id");
            session.close();
        }
    }

    @OnClose
    public void onClose(@NotNull Session session) {
        onlineService.offline(onlineService.getIdBySession(session), session);
    }

    @OnMessage
    public void onMessage(@NotNull String message_string, @NotNull Session session) throws IOException {
        try {
            var message_json = JSONObject.parseObject(message_string);
            var to = message_json.getString("to");
            var groupId = message_json.getString("group");
            if (to == null && groupId == null) {
                sendJsonErrorText(session, "to || group");
                return;
            }
            if (to != null && groupId != null) {
                sendJsonErrorText(session, "to && group");
                return;
            }
            var content = message_json.getJSONObject("content");
            if (content == null) {
                sendJsonErrorText(session, "content");
                return;
            }
            var time = String.valueOf(new java.sql.Timestamp(System.currentTimeMillis()));
            var from = onlineService.getIdBySession(session);
            JSONObject response;
            if (to != null) {
                response = messageService.sendMessageToPrivateChat(from, to, content, time);
            }
            else {
                response = messageService.sendMessageToGroupChat(from, groupId, content, time);
            }
            session.getBasicRemote().sendText(response.toString());
        }
        catch (JSONException e) {
            sendJsonErrorText(session, "json无法解析");
        }
    }

    @OnError
    public void onError(@NotNull Session session, @NotNull Throwable error) {
        System.err.println("Session" + session + "发生错误");
        error.printStackTrace();
        onlineService.offline(onlineService.getIdBySession(session), session);
    }

    private static void sendJsonErrorText(@NotNull Session session, @NotNull String key) throws IOException {
        var response = new JSONObject();
        response.put("type", "fail");
        response.put("time", String.valueOf(new java.sql.Timestamp(System.currentTimeMillis())));
        response.put("error", key);
        session.getBasicRemote().sendText(response.toString());
    }
}