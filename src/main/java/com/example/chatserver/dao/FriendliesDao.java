package com.example.chatserver.dao;

import com.example.chatserver.bean.Friendlies;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface FriendliesDao extends BaseDao<Friendlies>{
}
