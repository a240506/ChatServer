package com.example.chatserver.serve;

import com.example.chatserver.bean.GroupUsers;
import com.example.chatserver.bean.Groups;
import com.example.chatserver.common.Tool;
import com.example.chatserver.service.impl.GroupUsersServiceImpl;
import com.example.chatserver.service.impl.GroupsServiceImpl;
import com.example.chatserver.vo.CreateGroupsParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/*
文件创建于  2022/10/23  16：09
*/
@RestController
@RequestMapping("/groupUsers")
public class GroupUsersServe {
    @Autowired
    private GroupUsersServiceImpl groupUsersService;
    //TODO 这里没有添加群主
    @RequestMapping("/loadGroupUserByGroupId")
    public List<GroupUsers> loadGroupUserByGroupId(@RequestBody GroupUsers groupUsers){
        return  groupUsersService.loadGroupUserByGroupId(groupUsers.getGroupId());
    }
    //还没有测试
    @RequestMapping("/insert")
    public Boolean insert(@RequestBody GroupUsers groupUsers) {
        return groupUsersService.insert(groupUsers)==1L ? true :false;
    }



}
