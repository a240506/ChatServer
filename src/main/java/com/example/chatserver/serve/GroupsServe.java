package com.example.chatserver.serve;

import com.example.chatserver.bean.Friendlies;
import com.example.chatserver.bean.Groups;
import com.example.chatserver.bean.User;
import com.example.chatserver.common.Tool;
import com.example.chatserver.service.impl.FriendliesServiceImpl;
import com.example.chatserver.service.impl.GroupsServiceImpl;
import com.example.chatserver.service.impl.UserServiceImpl;
import com.example.chatserver.vo.CreateGroupsParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/*
文件创建于  2022/10/18  17：17
*/
@RestController
@RequestMapping("/groupsServe")
public class GroupsServe {
    @Autowired
    private GroupsServiceImpl groupsService;
    @Autowired
    private UserServiceImpl userService;

    @RequestMapping("/insert")
    public Boolean  insert(HttpServletRequest request, @RequestBody CreateGroupsParam groups) {
        String userName=(String)request.getSession().getAttribute("userName");
        User user= userService.loadByName(userName);
        groups.setHolderName(userName);
        groups.setHolderUserId(Tool.Long2Int(user.getUserId()));
        System.out.println(groups.getPhysicalPath()!=null);
        if(groups.getPhysicalPath()!=null){
            groups.setAvatar(Tool.selfMoveFile(groups.getPhysicalPath()));
        }
        return groupsService.insert(groups)==1L ? true :false;
    }

    @RequestMapping("/loadByHolderNameOrId")
    public List<Groups> loadByHolderNameOrId(HttpServletRequest request){
        Groups groups=new Groups();
        String userName=(String)request.getSession().getAttribute("userName");
        groups.setHolderName(userName);
        return  groupsService.loadByHolderNameOrId(groups);
    }
}
