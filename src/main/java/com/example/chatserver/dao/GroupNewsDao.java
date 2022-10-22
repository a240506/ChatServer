package com.example.chatserver.dao;

import com.example.chatserver.bean.GroupNews;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
@Mapper
@Repository
public interface GroupNewsDao extends BaseDao<GroupNews>{
    List<GroupNews> loadGroupNewsByGroupId(int groupId);
}
