package com.example.chatserver.service.impl;

import com.example.chatserver.bean.User;
import com.example.chatserver.dao.UserDao;
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
}
