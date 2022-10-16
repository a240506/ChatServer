package com.example.chatserver.serve;

import com.example.chatserver.bean.Friendlies;
import com.example.chatserver.bean.News;
import com.example.chatserver.bean.User;
import com.example.chatserver.service.impl.FriendliesServiceImpl;
import com.example.chatserver.service.impl.NewsServiceImpl;
import com.example.chatserver.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/*
文件创建于  2022/10/15  12：54
*/
@RestController
@RequestMapping("/friendliesServe")
public class FriendliesServe {
    @Autowired
    private FriendliesServiceImpl friendliesService;
    @Autowired
    private UserServiceImpl userService;

    @RequestMapping("/insert")
    public Boolean  friendliesInsert(@RequestBody Friendlies friendlies) {

        return friendliesService.insert(friendlies)==1L ? true :false;
    }

    @RequestMapping("/info")
    public List<User>  getFriendInfo(HttpServletRequest request) {
        List<User> list=new ArrayList<>();
        User user= userService.loadByName((String)request.getSession().getAttribute("userName"));
        //默认添加自己，保证有用户
        list.add(user);
        List<Friendlies> friendlies= friendliesService.loadFriendByUserYAndType(Integer.parseInt(user.getUserId().toString()));
        //这里时添加好友的信息
        for(Friendlies item : friendlies){
            if(item.getUserY()==Integer.parseInt(user.getUserId().toString())){
                list.add(userService.loadById(item.getUserX()));
            }else{
                list.add(userService.loadById(item.getUserY()));
            }
        }

        return list;
    }


}
