package com.example.chatserver.service.impl;

import com.example.chatserver.bean.AddMessage;
import com.example.chatserver.bean.Friendlies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
文件创建于  2022/10/15  14：29
*/
@Service("addMessageService")
public class AddMessageServiceImpl {
    @Autowired
    private com.example.chatserver.dao.AddMessageDao addMessageDao;
    @Autowired
    private UserServiceImpl userService;
    public Long insert(AddMessage addMessage) {
        return addMessageDao.insert(addMessage);
    }

    /**
     * 判断用户之间是否已经是好友
     * @param addMessage
     * @return
     */
    public boolean isMessageExist(AddMessage addMessage){
        //System.out.println(addMessageDao.addMessageDaoByUserXdAndUserY(addMessage));
        if(addMessageDao.addMessageDaoByUserXdAndUserY(addMessage).size()==0){
            return false;
        }
        return true;
    }
    public List<AddMessage> loadAddMessageByUserYAndType(AddMessage addMessage){
        List<AddMessage> list=addMessageDao.loadAddMessageByUserYAndType(addMessage);
        return list;
    }
}
