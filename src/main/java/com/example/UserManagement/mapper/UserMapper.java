package com.example.UserManagement.mapper;

import com.example.UserManagement.entity.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

/**
 * mapper的具体表达式
 */
@Mapper //标记mapper文件位置，否则在Application.class启动类上配置mapper包扫描
@Repository
public interface UserMapper {

    /**
     * 查询用户名是否存在，若存在，不允许注册
     * 注解@Param(value) 若value与可变参数相同，注解可省略
     * 注解@Results  列名和字段名相同，注解可省略
     * @param id
     * @return
     */
    @Select(value = "select u.id,u.password from user u where u.id=#{id}")
    @Results
            ({@Result(property = "id",column = "id"),
                    @Result(property = "password",column = "password")})
    User findUserById(@Param("id") int id);

    /**
     * 注册  插入一条user记录
     * @param user
     * @return
     */
    @Insert("insert into user values(id=#{id},name=#{username},password=#{password})")
    //加入该注解可以保存对象后，查看对象插入id
    @Options(useGeneratedKeys = true,keyProperty = "id",keyColumn = "id")
    void regist(User user);

    /**
     * 登录
     * @param user
     * @return
     */
    @Select("select u.name from user u where u.id = #{id} and password = #{password}")
    String login(User user);
}