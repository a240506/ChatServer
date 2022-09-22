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
    //private HttpSession httpSession;

    /*建立时调用*/
    @OnOpen
    public void onOpen(Session session, EndpointConfig endpointConfig) {
        System.out.println("wbSocket建立连接onOpen！");
        this.session = session;
        //获取httpsession域中存放的username对应的值

        //String username = (String) httpSession.getAttribute("username");
        //System.out.println(httpSession);
        //System.out.println(httpSession.getAttribute("userName"));
        String username="1111";
        //存放到onlineUsers中保存
        onlineUsers.put(username, this);

        broadcastMsgToAllOnlineUsers(username);
    }

    /**
     * .
     * @param message 给客户端发送消息
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
        //String username = (String) httpSession.getAttribute("username");
        ////
        //ChatEndpoint remove = onlineUsers.remove(username);
        ////广播
        //System.out.println(username+"下线");
    }
}
