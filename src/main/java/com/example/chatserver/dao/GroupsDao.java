package com.example.chatserver.dao;

import com.example.chatserver.bean.Friendlies;
import com.example.chatserver.bean.Groups;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/*
文件创建于  2022/10/18  17：02
*/
@Mapper
@Repository
public interface GroupsDao extends BaseDao<Groups>{
    List<Groups> loadByHolderNameOrId(Groups groups);
}
