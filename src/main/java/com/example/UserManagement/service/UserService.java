package com.example.UserManagement.service;

import com.example.UserManagement.bean.Result;

import com.example.UserManagement.entity.Group;
import com.example.UserManagement.entity.User;
import com.example.UserManagement.mapper.UserMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
@Transactional(rollbackFor = RuntimeException.class)
public class UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private SecurityService securityService;
    /**
     * 注册
     * @param user 参数封装
     * @return Result
     */
    public Result regist(User user) {
        Result result = new Result();
        result.setSuccess(false);
        result.setDetail(null);
        try {
            int qqNumber = (int)(100000000+Math.random()*(999999999-1+1));
            User existUser = userMapper.findUserById(qqNumber);
            if(existUser != null){
                //如果id已存在
                while (existUser != null) {
                    qqNumber = (int)(100000000+Math.random()*(999999999-1+1));
                    existUser = userMapper.findUserById(qqNumber);
                }

            }else{
                user.setId(qqNumber);
                userMapper.regist(user);
                //System.out.println(user.getId());
                result.setMsg("注册成功");
                result.setSuccess(true);
                result.setDetail(user);
            }
        } catch (Exception e) {
            result.setMsg(e.getMessage());
            e.printStackTrace();
        }
        return result;
    }
    /**
     * 登录
     * @param user 用户名和密码
     * @return Result
     */
    public Result login(User user) {
        Result result = new Result();
        result.setSuccess(false);
        result.setDetail(null);
        try {
            String userName= userMapper.login(user);
            if(userName == null){
                result.setMsg("用户名或密码错误");
                result.setSuccess(false);
            }else{
                result.setMsg("登录成功");
                result.setSuccess(true);
                user.setId(user.getId());
                user.setUsername(userName);
                result.setDetail(user);
            }
        } catch (Exception e) {
            result.setMsg(e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    public Result<List<User>> getFriendList(int id){
        Result<List<User>> result = new Result<>();
        try{
            List<User> userList = userMapper.findUsersById(id);
            result.setDetail(userList);
            result.setSuccess(true);
            result.setMsg("获取成功");
        } catch (Exception e){
            result.setMsg(e.getMessage());
            result.setSuccess(false);
            e.printStackTrace();
        }
        return result;
    }

    public Result<List<Group>> getGroupList(int id){
        Result<List<Group>> result = new Result<>();
        try{
            List<Group> groupList = userMapper.findGroupsById(id);
            result.setDetail(groupList);
            result.setSuccess(true);
            result.setMsg("获取成功");
        } catch (Exception e){
            result.setMsg(e.getMessage());
            result.setSuccess(false);
            e.printStackTrace();
        }
        return result;
    }
}
