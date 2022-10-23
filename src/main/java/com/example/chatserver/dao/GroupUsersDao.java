package com.example.chatserver.dao;

import com.example.chatserver.bean.GroupUsers;
import com.example.chatserver.bean.Groups;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/*
文件创建于  2022/10/23  15：33
*/
@Mapper
@Repository
public interface GroupUsersDao extends BaseDao<GroupUsers>{
    List<GroupUsers> loadGroupUserByGroupId(int groupId);
}
