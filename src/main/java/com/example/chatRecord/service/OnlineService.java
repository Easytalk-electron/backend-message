package com.example.chatRecord.service;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import javax.websocket.Session;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class OnlineService {

    private static AtomicInteger sessionNum = new AtomicInteger(0);
    private static Map<String, Session> idSessionMap = new ConcurrentHashMap<>();
    private static Map<Session, String> sessionIdMap = new ConcurrentHashMap<>();

    public Session getSessionById(@NotNull String id) {
        return idSessionMap.get(id);
    }

    public String getIdBySession(@NotNull Session session) {
        return sessionIdMap.get(session);
    }

    public void online(@NotNull String id, @NotNull Session session) {
        int n = sessionNum.addAndGet(1);
        System.out.println(String.format("用户%s上线了，当前在线人数为：%d", id, n));
        idSessionMap.put(id, session);
        sessionIdMap.put(session, id);
    }

    public void offline(@NotNull String id, @NotNull Session session) {
        int n = sessionNum.addAndGet(-1);
        System.out.println(String.format("用户%s下线了，当前在线人数为：%d", id, n));
        sessionIdMap.remove(session);
        idSessionMap.remove(id);
    }
}
