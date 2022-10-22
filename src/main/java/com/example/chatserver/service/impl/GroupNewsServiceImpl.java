package com.example.chatserver.service.impl;

import com.example.chatserver.bean.GroupNews;
import com.example.chatserver.bean.Groups;
import com.example.chatserver.common.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/*
文件创建于  2022/10/22  22：39
*/
@Service("groupNewsService")
public class GroupNewsServiceImpl {
    @Autowired
    private com.example.chatserver.dao.GroupNewsDao groupNewsDao;

    public Long insert(GroupNews groupNews) {
        groupNews.setTime(Tool.getTimeString("yyyy-MM-dd HH:mm:ss"));
        return groupNewsDao.insert(groupNews);
    }
    public List<GroupNews> loadGroupNewsByGroupId(int groupId){
        return groupNewsDao.loadGroupNewsByGroupId(groupId);
    }
}
