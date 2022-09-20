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

    /*建立时调用*/
    @OnOpen
    public void onOpen(Session session, EndpointConfig endpointConfig) {
        System.out.println("wbSocket建立连接onOpen！");

    }



}
