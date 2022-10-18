package com.example.chatserver.service.impl;

import com.example.chatserver.bean.Friendlies;
import com.example.chatserver.bean.Groups;
import com.example.chatserver.common.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/*
文件创建于  2022/10/18  17：09
*/
@Service("groupsService")
public class GroupsServiceImpl {
    @Autowired
    private com.example.chatserver.dao.GroupsDao groupsDao;

    public Long insert(Groups groups) {
        groups.setCreateDate(Tool.getTimeString("yyyy-MM-dd HH:mm:ss"));
        return groupsDao.insert(groups);
    }

    public List<Groups> loadByHolderNameOrId(Groups groups){

        return  groupsDao.loadByHolderNameOrId(groups);
    }
}
