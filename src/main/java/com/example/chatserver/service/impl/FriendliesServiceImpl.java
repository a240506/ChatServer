package com.example.chatserver.service.impl;

import com.example.chatserver.bean.Friendlies;
import com.example.chatserver.bean.News;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/*
文件创建于  2022/10/15  11：02
*/
@Service("friendliesServ")
public class FriendliesServiceImpl {
    @Autowired
    private com.example.chatserver.dao.FriendliesDao friendliesDao;

    public Long insert(Friendlies friendlies) {
        return friendliesDao.insert(friendlies);
    }

    public List<Friendlies> loadFriendByUserYAndType(int userX){return friendliesDao.loadFriendByUserYAndType(userX);}
}
