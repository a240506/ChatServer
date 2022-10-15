package com.example.chatserver.dao;

import com.example.chatserver.bean.AddMessage;
import com.example.chatserver.bean.PYQNews;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface AddMessageDao extends BaseDao<AddMessage>{
    List<AddMessage> addMessageDaoByUserXdAndUserY(AddMessage addMessage);

    List<AddMessage> loadAddMessageByUserYAndType(AddMessage addMessage);
}
