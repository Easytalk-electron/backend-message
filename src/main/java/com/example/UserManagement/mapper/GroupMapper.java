package com.example.UserManagement.mapper;

import com.example.UserManagement.entity.GuRelation;
import com.example.UserManagement.entity.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface GroupMapper {
    @Select("select * from guRelation where groupid=#{id}")
    List<User> findUsersByGroupId(@Param("id") int id);

    @Select(value = "select * from guRelation where groupid=#{gid} and userid=#{uid}")
    @Results
            ({@Result(property = "gid", column = "groupid"),
                    @Result(property = "uid", column = "userid")})
    GuRelation findUserById(@Param("gid") long gid, @Param("uid") long uid);

    @Insert("insert into guRelation (userid, groupid) values(#{uid},#{gid})")
    void addUser(GuRelation guRelation);
}
