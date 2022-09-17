package com.example.chatserver.dao;

import com.example.chatserver.bean.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/*
文件创建于  2022/09/17  15：44
*/
@Mapper
@Repository
public interface UserDao extends BaseDao<User>{
    User loadByName(String name);
}
