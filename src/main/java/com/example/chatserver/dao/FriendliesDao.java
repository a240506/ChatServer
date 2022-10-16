package com.example.chatserver.dao;

import com.example.chatserver.bean.AddMessage;
import com.example.chatserver.bean.Friendlies;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface FriendliesDao extends BaseDao<Friendlies>{
    List<Friendlies> loadFriendByUserYAndType(int userX);
}
