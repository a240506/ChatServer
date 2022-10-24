package com.example.chatserver.serve;

import com.example.chatserver.bean.AddMessage;
import com.example.chatserver.bean.Friendlies;
import com.example.chatserver.bean.Groups;
import com.example.chatserver.common.enumType.add_message_type;
import com.example.chatserver.service.impl.AddMessageServiceImpl;
import com.example.chatserver.service.impl.FriendliesServiceImpl;
import com.example.chatserver.service.impl.GroupsServiceImpl;
import com.example.chatserver.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
文件创建于  2022/10/15  23：21
*/
@RestController
@RequestMapping("/addMessageServe")
public class AddMessageServe {
    @Autowired
    private AddMessageServiceImpl addMessageService;
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private FriendliesServiceImpl friendliesService;
    @Autowired
    private GroupsServiceImpl groupsService;
    /**
     * 获取 UserY 的消息
     * @param addMessage
     * @return
     */
    @RequestMapping("/byUserYAndType")
    public List<Object> byUserYAndType(@RequestBody AddMessage addMessage) {
        //System.out.println(addMessage);
        List<Object> res=new ArrayList<>();
        if(addMessage.getType()== add_message_type.friend){
            List<AddMessage> list=addMessageService.loadAddMessageByUserYAndType(addMessage);
            for(AddMessage item : list){
                Map<String,Object> tmep=new HashMap<>();
                tmep.put("userX",userService.loadById(item.getUserX()));
                tmep.put("type",item.getType());
                tmep.put("message",item.getMessage());
                tmep.put("userY",userService.loadById(item.getUserY()));
                tmep.put("_id",item.get_id());
                res.add(tmep);
            }
        }else if(addMessage.getType()== add_message_type.group){
            Groups temp=new Groups();
            temp.setHolderUserId(addMessage.getUserY());
            //获取到用户userY创建的群
            List<Groups> groupsList=groupsService.loadByHolderNameOrId(temp);
            for(Groups groups:groupsList){
                //重新设置 UserY ，UserY应该是群id
                addMessage.setUserY(groups.get_id());
                List<AddMessage> list=addMessageService.loadAddMessageByUserYAndType(addMessage);
                for(AddMessage item : list){
                    Map<String,Object> tmep=new HashMap<>();
                    tmep.put("userX",userService.loadById(item.getUserX()));
                    tmep.put("type",item.getType());
                    tmep.put("message",item.getMessage());
                    tmep.put("userY",userService.loadById(item.getUserY()));
                    tmep.put("_id",item.get_id());
                    res.add(tmep);
                }
            }


            //System.out.println(addMessage);
            //addMessage.getUserY();
        //    这里是获取用户y的群消息

        }

        return res;
    }

    @RequestMapping("/delete")
    public Boolean delete(@RequestBody Map<String, Object> params){
        int id= (int) params.get("id");

        return addMessageService.delete(id);
    }

    @RequestMapping("/consent")
    public Boolean consent(@RequestBody Map<String, Object> params){
        int id= (int) params.get("id");
        AddMessage addMessage= addMessageService.loadById(id);
        Friendlies friendlies=new Friendlies();
        friendlies.setUserX(addMessage.getUserX());
        friendlies.setUserY(addMessage.getUserY());
        // TODO 这里没有判断是否已经存在关系
        if(friendliesService.insert(friendlies)==1l){
            return  addMessageService.delete(id);
        }
        return false;
    }

}
