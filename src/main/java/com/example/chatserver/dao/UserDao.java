package com.example.chatserver.dao;

import com.example.chatserver.bean.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/*
文件创建于  2022/09/17  15：44
*/
@Mapper
@Repository
public interface UserDao extends BaseDao<User>{
    User loadByName(String name);
    User loadById(int id);
    User login(@Param("userName") String username,@Param("password") String password,@Param("type") String type);
    List<User> loadLikeName(String name);
}
