package com.example.chatserver.service.impl;

import com.example.chatserver.bean.User;
import com.example.chatserver.dao.UserDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*
文件创建于  2022/09/17  16：26
*/
@Service("userService")
public class UserServiceImpl {
    @Autowired
    private UserDao UserDao;

    public User loadByName(String name) {
        return UserDao.loadByName(name);
    }
    //用户登录
    public User userLogin(String username,  String password) {
        return UserDao.login(username,password,"user");
    }
    //管理员登录
    public User adminLogin(String username,  String password) {
        return UserDao.login(username,password,"admin");
    }
}
