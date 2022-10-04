package com.example.chatserver.service.impl;

import com.example.chatserver.bean.News;
import com.example.chatserver.bean.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*
文件创建于  2022/10/04  11：06
*/
@Service("newsService")
public class NewsServiceImpl {
    @Autowired
    private com.example.chatserver.dao.NewsDao newsDao;

    public Long insert(News bean) {
        return newsDao.insert(bean);
    }
}
