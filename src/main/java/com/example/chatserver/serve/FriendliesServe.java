package com.example.chatserver.serve;

import com.example.chatserver.bean.Friendlies;
import com.example.chatserver.bean.News;
import com.example.chatserver.service.impl.FriendliesServiceImpl;
import com.example.chatserver.service.impl.NewsServiceImpl;
import com.example.chatserver.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/*
文件创建于  2022/10/15  12：54
*/
@RestController
@RequestMapping("/friendliesServe")
public class FriendliesServe {
    @Autowired
    private FriendliesServiceImpl friendliesService;

    @RequestMapping("/insert")
    public Boolean  friendliesInsert(@RequestBody Friendlies friendlies) {

        return friendliesService.insert(friendlies)==1L ? true :false;
    }
}
