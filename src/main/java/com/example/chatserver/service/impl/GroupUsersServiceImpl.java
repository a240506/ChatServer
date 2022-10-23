package com.example.chatserver.service.impl;

import com.example.chatserver.bean.GroupUsers;
import com.example.chatserver.bean.Groups;
import com.example.chatserver.common.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/*
文件创建于  2022/10/23  16：03
*/
@Service("groupUsersService")
public class GroupUsersServiceImpl {
    @Autowired
    private com.example.chatserver.dao.GroupUsersDao groupUsersDao;

    public List<GroupUsers> loadGroupUserByGroupId(int groupId){
        return groupUsersDao.loadGroupUserByGroupId(groupId);
    }

    public Long insert(GroupUsers groupUsers) {
        groupUsers.setCreateDate(Tool.getTimeString("yyyy-MM-dd HH:mm:ss"));
        return groupUsersDao.insert(groupUsers);
    }


}
