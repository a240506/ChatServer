package com.example.chatserver.dao;

import com.example.chatserver.bean.PYQNews;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/*
文件创建于  2022/10/08  16：49
*/
@Mapper
@Repository
public interface PYQNewsDao extends BaseDao<PYQNews>{
    List<PYQNews> getPYQByName(String name);

}
