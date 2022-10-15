package com.example.chatserver.serve;

import com.example.chatserver.bean.AddMessage;
import com.example.chatserver.bean.Friendlies;
import com.example.chatserver.service.impl.AddMessageServiceImpl;
import com.example.chatserver.service.impl.FriendliesServiceImpl;
import com.example.chatserver.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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

    /**
     * 获取 UserY 的消息
     * @param addMessage
     * @return
     */
    @RequestMapping("/byUserYAndType")
    public List<Object> friendliesInsert(@RequestBody AddMessage addMessage) {
        List<AddMessage> list=addMessageService.loadAddMessageByUserYAndType(addMessage);
        List<Object> res=new ArrayList<>();
        for(AddMessage item : list){
            Map<String,Object> tmep=new HashMap<>();
            tmep.put("userX",userService.loadById(item.getUserX()));
            tmep.put("type",item.getType());
            tmep.put("message",item.getMessage());
            tmep.put("userY",userService.loadById(item.getUserY()));
            res.add(tmep);
        }
        return res;
    }
}
