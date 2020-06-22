package com.example.UserManagement.service;

import com.example.UserManagement.bean.Result;
import com.example.UserManagement.entity.GuRelation;
import com.example.UserManagement.entity.User;
import com.example.UserManagement.mapper.GroupMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = RuntimeException.class)
public class GroupService {
    @Autowired
    GroupMapper groupMapper;

    public Result<List<User>> getGroupMembers(int id){
        Result<List<User>> result = new Result<>();
        try{
            List<User> userList = groupMapper.findUsersByGroupId(id);
            result.setMsg("获取成功");
            result.setSuccess(true);
            result.setDetail(userList);
        } catch (Exception e){
            result.setMsg(e.getMessage());
            result.setSuccess(false);
            e.printStackTrace();
        }
        return result;
    }

    public Result addGroupMember(int gid, int uid){
        Result result = new Result();
        try{
            GuRelation guRelation = groupMapper.findUserById(gid, uid);
            if (guRelation!=null){
                result.setMsg("此人已在群聊");
                result.setSuccess(false);
            }
            else {
                guRelation = new GuRelation();
                guRelation.setGid(gid);
                guRelation.setUid(uid);
                groupMapper.addUser(guRelation);
                result.setMsg("添加成功");
                result.setSuccess(true);
                result.setDetail(guRelation);
            }
        } catch (Exception e){
            result.setMsg(e.getMessage());
            result.setSuccess(false);
            e.printStackTrace();
        }
        return result;
    }
}
