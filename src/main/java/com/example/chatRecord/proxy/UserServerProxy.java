package com.example.chatRecord.proxy;

import com.alibaba.fastjson.JSONObject;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

@Component
public class UserServerProxy {

    public String[] queryReceiversInGroup(@NotNull String group) {
        try {
            URL url = new URL("http://140.143.163.211:8080/group/userList");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestMethod("GET");
            connection.setUseCaches(false);
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            var requestBody = new JSONObject();
            requestBody.put("id", group);
            connection.getOutputStream().write(requestBody.toString().getBytes());
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
            String line;
            StringBuilder response = new StringBuilder();
            while ((line = br.readLine()) != null) {
                response.append(line + "\n");
            }
            connection.disconnect();
            var jsonArray = JSONObject.parseArray(String.valueOf(response));
            var result = new String[jsonArray.size()];
            Arrays.setAll(result, i -> jsonArray.getJSONObject(i).getString("id"));
            return result;
        }
        catch (Exception e) {
            e.printStackTrace();
            return new String[0];
        }
    }
}
