package com.example.chatserver.ws;

import com.example.chatserver.bean.User;
import com.example.chatserver.service.impl.UserServiceImpl;
import com.example.chatserver.vo.SocketMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/*
文件创建于  2022/09/20  23：10
*/
//这里注释可以将目前的类定义成一个websocket服务器端
@ServerEndpoint(value = "/chat", configurator = GetHttpSessionConfigurator.class)
@Component
public class ChatEndpoint {
    @Autowired
    private UserServiceImpl userService;

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

    //TODO 考虑用户没有登录问题
    /*建立时调用*/
    @OnOpen
    public void onOpen(Session session, EndpointConfig endpointConfig) {
        this.session = session;
        //获取httpsession对象
        httpSession = (HttpSession) endpointConfig.getUserProperties().get(HttpSession.class.getName());
        //获取用户名
        userName = (String) httpSession.getAttribute("userName");
        //检查用户是否存在,用户存在就强制下线
        checkUserExists(userName);
        //重新保存下当前用户的用户名
        httpSession.setAttribute("userName",userName);
        //存放到onlineUsers中保存
        onlineUsers.put(userName, this);

        broadcastMsgToAllOnlineUsers(userName+"上线","success");
        LOGGER.info(userName+"上线");
    }

    /**
     * @param message 给客所有户端发送消息
     * @return void
     */
    private void broadcastMsgToAllOnlineUsers(String message,String type) {
        //所有登录用户名称
        Set<String> names = onlineUsers.keySet();

        for (String name : names) {
            ChatEndpoint chatEndpoint = onlineUsers.get(name);
            chatEndpoint.sendSystemMessage(message,type);

        }
    }
    /**关闭时调用*/
    @OnClose
    public void onClose(Session session) {
        //需要移除下线的用户
        onlineUsers.remove(userName);
        LOGGER.info(userName+"下线");
        //广播
        broadcastMsgToAllOnlineUsers(userName+"下线","error");
    }

    /**
     * 接收到客户端发送的数据时调用.
     * @param message 客户端发送的数据
     * @param session session对象
     * @return void
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        ObjectMapper objectMapper = new ObjectMapper();
        SocketMessage msg= null;
        try {
            msg = objectMapper.readValue(message, SocketMessage.class);
        } catch (JsonProcessingException e) {
            LOGGER.error("接收客户端的消息，转换出错了！");
            e.printStackTrace();
        }
        SocketMessage resMsg=new SocketMessage();
        String event=msg.getEvent();

        // TODO 这里可以用反射来改造，会少很多代码，也会好使用
        if(event.equals("getOnlineUsersInfo")){
            resMsg.setEvent("getOnlineUsersInfo");
            resMsg.setData(getOnlineUsersInfo());
        }else{
            //这里没有什么事件就发送什么事件，也可以 发 error 事件
            resMsg.setEvent(event);
            Map<String,Object> map=new HashMap<>();
            map.put("msg","错误，服务器没有接受这个事件的方法");
            resMsg.setData(map);
        }
        //发送数据
        this.send(resMsg.toJSONString());

    }

    /**
     * 检查用户是否存在,用户存在就强制下线
     * @param userName 用户名
     */
    private void checkUserExists(String userName){
        ChatEndpoint chatEndpoint = onlineUsers.get(userName);
        if(chatEndpoint!=null){
        //    用户重复登录
            //获取推送对象
            RemoteEndpoint.Basic basicRemote = chatEndpoint.session.getBasicRemote();
            try {
                LOGGER.info(userName+"被挤占下线");
                SocketMessage socketMessage=new SocketMessage("system",new HashMap<>());
                socketMessage.getData().put("message","你已经被挤占下线");
                socketMessage.getData().put("type","error");
                basicRemote.sendText(socketMessage.toJSONString());
                //删除下线的用户名
                chatEndpoint.httpSession.setAttribute("userName",null);
                chatEndpoint.session.getBasicRemote();
                chatEndpoint.session.close();
                onlineUsers.remove(userName);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * 发送本连接用户的信息
     * @param msg
     */
    private void send(String msg){
        //获取推送对象
        RemoteEndpoint.Basic basicRemote = this.session.getBasicRemote();
        try {
            basicRemote.sendText(msg);
        } catch (IOException e) {
            LOGGER.error("发送消息失败,消息为:"+msg.toString(), e);
            e.printStackTrace();
        }
    }

    //获取所有在线用户信息
    private Map<String,Object> getOnlineUsersInfo(){

        System.out.println(userService);
        //所有登录用户名称
        //Set<String> names = onlineUsers.keySet();
        //List<User> OnlineUsers=new ArrayList<>();
        //for (String name : names){
        //    //User user=userService.loadByName(name);
        //    System.out.println(userService);
        //    //OnlineUsers.add(user);
        //}
        Map<String,Object> map=new HashMap<>();
        map.put("data","OnlineUsers");
        return map;
    }

    /**
     * 发送系统消息
     * @param message
     * @param type 'success' | 'warning' | 'info' | 'error'
     */
    private void sendSystemMessage(String message,String type){
        SocketMessage socketMessage=new SocketMessage("system",new HashMap<>());
        socketMessage.getData().put("message",message);
        socketMessage.getData().put("type",type);
        this.send(socketMessage.toJSONString());
    }
}
