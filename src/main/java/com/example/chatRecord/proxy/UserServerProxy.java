package com.example.chatRecord.proxy;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserServerProxy {

    public List<String> queryReceiversInGroup(@NotNull String group) {
        return null;
    }
}
