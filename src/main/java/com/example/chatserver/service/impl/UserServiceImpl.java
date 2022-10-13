package com.example.chatserver.service.impl;

import com.example.chatserver.bean.User;
import com.example.chatserver.common.Tool;
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
    private UserDao userDao;

    public User loadByName(String name) {
        return userDao.loadByName(name);
    }
    //用户登录
    public User userLogin(String username,  String password) {
        return userDao.login(username,password,"user");
    }
    //管理员登录
    public User adminLogin(String username,  String password) {
        return userDao.login(username,password,"admin");
    }
    //用户注册
    public Long userRegister(User bean) {
        bean.setType("user");
        return userDao.insert(bean);
    }
    //管理员注册
    public Long adminRegister(User bean) {
        bean.setType("admin");
        return userDao.insert(bean);
    }

    /**
     * 用户信息更新
     * @param bean
     * @return
     */
    public Long update(User bean){
        return userDao.update(bean);
    }

    public Long updateLastLoginTime(String userName){
        User user= userDao.loadByName(userName);
        user.setLastLoginTime(Tool.getTimeString("yyyy-MM-dd HH:mm:ss"));
        return update(user);
    }

}
