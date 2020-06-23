package com.example.chatRecord.service;

import com.alibaba.fastjson.JSONObject;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import javax.websocket.Session;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class OnlineService {

    private static Map<String, Session> idSessionMap = new ConcurrentHashMap<>();
    private static Map<Session, String> sessionIdMap = new ConcurrentHashMap<>();

    public Session getSessionById(@NotNull String id) {
        return idSessionMap.get(id);
    }

    public String getIdBySession(@NotNull Session session) {
        return sessionIdMap.get(session);
    }

    public void online(@NotNull String id, @NotNull Session session) throws IOException {
        var old_session = idSessionMap.get(id);
        idSessionMap.put(id, session);
        System.out.println(String.format("用户%s上线了，当前在线人数为：%d", id, idSessionMap.size()));
        if (old_session != null) {
            var kicked = new JSONObject();
            kicked.put("type", "kicked");
            old_session.getBasicRemote().sendText(kicked.toString());
            old_session.close();
            sessionIdMap.remove(old_session);
        }
        sessionIdMap.put(session, id);
    }

    public void offline(@NotNull String id, @NotNull Session session) {
        sessionIdMap.remove(session);
        if (idSessionMap.get(id) == session) {
            idSessionMap.remove(id);
            System.out.println(String.format("用户%s下线了，当前在线人数为：%d", id, idSessionMap.size()));
        }
    }
}
