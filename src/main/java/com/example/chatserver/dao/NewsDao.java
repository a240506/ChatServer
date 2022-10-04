package com.example.chatserver.dao;

import com.example.chatserver.bean.News;
import com.example.chatserver.bean.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/*
文件创建于  2022/10/04  10：58
*/
@Mapper
@Repository
public interface NewsDao extends BaseDao<News>{
}
