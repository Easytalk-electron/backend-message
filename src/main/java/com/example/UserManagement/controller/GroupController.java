package com.example.UserManagement.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.UserManagement.bean.Result;
import com.example.UserManagement.entity.Group;
import com.example.UserManagement.entity.Login;
import com.example.UserManagement.entity.User;
import com.example.UserManagement.service.GroupService;
import com.example.UserManagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/group")
public class GroupController {
    @Autowired
    private GroupService groupService;

    @RequestMapping("/userList")
    public JSONArray getGroupMembers(@RequestBody Login login) {
        String id = login.getId();
        int id1 = Integer.parseInt(id);
        Result result = groupService.getGroupMembers(id1);
        var userList = (List<User>)result.getDetail();
        JSONArray ja = new JSONArray();
        if (userList!=null)
            for (int i = 0; i<userList.size(); ++i){
                JSONObject jo = JSON.parseObject(JSON.toJSONString(userList.get(i)));
                ja.add(jo);
            }



        return ja;
    }

    @RequestMapping("/addUser")
    public JSONObject addUser(@RequestBody Group group){
        String gid = group.getGid();
        String new_uid = group.getNew_uid();
        int gid1 = Integer.parseInt(gid);
        int new_uid1 = Integer.parseInt(new_uid);
        Result result = groupService.addGroupMember(gid1,new_uid1);
        JSONObject jo = JSONObject.parseObject(JSONObject.toJSONString(result));
        return jo;
    }
}
