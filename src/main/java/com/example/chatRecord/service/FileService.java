package com.example.chatRecord.service;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.Serializable;
import java.util.concurrent.TimeUnit;

@Service
public class FileService {

    @Autowired
    private RedisTemplate<String, Serializable> redisTemplate;

    public void upload(@NotNull MultipartFile src, @NotNull String id) throws IOException {
        redisTemplate.opsForValue().set(generateRedisKeyForFile(id), src.getBytes(), 7, TimeUnit.DAYS);
    }

    public byte[] download(@NotNull String id) {
        return (byte[]) redisTemplate.opsForValue().get(generateRedisKeyForFile(id));
    }

    private String generateRedisKeyForFile(@NotNull String id) {
        return String.format("file(%s)", id);
    }
}
