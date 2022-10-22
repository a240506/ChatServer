package com.example.chatserver.serve;

import com.example.chatserver.bean.GroupNews;
import com.example.chatserver.bean.User;
import com.example.chatserver.common.Tool;
import com.example.chatserver.service.impl.GroupNewsServiceImpl;
import com.example.chatserver.service.impl.GroupsServiceImpl;
import com.example.chatserver.vo.CreateGroupsParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/*
文件创建于  2022/10/22  22：44
*/
@RestController
@RequestMapping("/groupNews")
public class GroupNewsServe {
    @Autowired
    private GroupNewsServiceImpl groupNewsService;
    @RequestMapping("/insert")
    public Boolean  insert(HttpServletRequest request, @RequestBody GroupNews groupNews) {
        return groupNewsService.insert(groupNews)==1L ? true :false;
    }

    @RequestMapping("/loadGroupNewsByGroupId")
    public List<GroupNews> loadGroupNewsByGroupId(@RequestBody GroupNews groupNews) {
        return groupNewsService.loadGroupNewsByGroupId(groupNews.getGroupId());
    }
}
