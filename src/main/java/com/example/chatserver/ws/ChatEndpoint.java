package com.example.chatserver.ws;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/*
文件创建于  2022/09/20  23：10
*/
//这里注释可以将目前的类定义成一个websocket服务器端
@ServerEndpoint(value = "/chat", configurator = GetHttpSessionConfigurator.class)
@Component
public class ChatEndpoint {

    private final static Logger LOGGER = LogManager.getLogger(ChatEndpoint.class);

    /**
     * 用来储存在线用户的容器
     */
    public static Map<String, ChatEndpoint> onlineUsers = new ConcurrentHashMap<>();

    /**
     * 用来给客户端发送消息
     */
    private Session session;

    /**
     * 用来获取在登录成功后，放在httpsession域中存放的username
     */
    private HttpSession httpSession;
    //用户名，唯一标识一个用户的
    private String userName;

    //TODO 考虑同一用户登录问题
    //TODO 考虑用户没有登录问题
    /*建立时调用*/
    @OnOpen
    public void onOpen(Session session, EndpointConfig endpointConfig) {
        this.session = session;
        //获取httpsession对象
        httpSession = (HttpSession) endpointConfig.getUserProperties().get(HttpSession.class.getName());
        //获取用户名
        userName = (String) httpSession.getAttribute("userName");
        if(userName==null){
            broadcastMsgToAllOnlineUsers("用户没有登录，请去登录");
            return;
        }
        //存放到onlineUsers中保存
        onlineUsers.put(userName, this);

        System.out.println(userName+"上线");
        broadcastMsgToAllOnlineUsers(userName+"上线");
    }

    /**
     * .
     * @param message 给客所有户端发送消息
     * @return void
     */
    private void broadcastMsgToAllOnlineUsers(String message) {
        //所有登录用户名称
        Set<String> names = onlineUsers.keySet();

        for (String name : names) {
            ChatEndpoint chatEndpoint = onlineUsers.get(name);
            //获取推送对象
            RemoteEndpoint.Basic basicRemote = chatEndpoint.session.getBasicRemote();
            try {
                basicRemote.sendText(message);
            } catch (IOException e) {
                LOGGER.error("广播发送系统消息失败！{}", e);
                e.printStackTrace();
            }
        }
    }
    /**关闭时调用*/
    @OnClose
    public void onClose(Session session) {
        //需要移除下线的用户
        onlineUsers.remove(userName);
        //广播
        broadcastMsgToAllOnlineUsers(userName+"下线");
    }
}
