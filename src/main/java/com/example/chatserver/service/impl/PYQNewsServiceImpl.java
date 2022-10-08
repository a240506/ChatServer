package com.example.chatserver.service.impl;

import com.example.chatserver.bean.News;
import com.example.chatserver.bean.PYQNews;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/*
文件创建于  2022/10/08  16：54
*/
@Service("PYQNewsService")
public class PYQNewsServiceImpl {
    @Autowired
    private com.example.chatserver.dao.PYQNewsDao pyqNewsDao;

    public Long insert(PYQNews bean) {
        return pyqNewsDao.insert(bean);
    }

    public List<PYQNews> getPYQByName(String name){
        return pyqNewsDao.getPYQByName(name);
    }
}
