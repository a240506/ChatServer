package com.example.chatserver.ws;

import com.example.chatserver.bean.News;
import com.example.chatserver.bean.User;
import com.example.chatserver.common.Tool;
import com.example.chatserver.service.impl.NewsServiceImpl;
import com.example.chatserver.service.impl.UserServiceImpl;
import com.example.chatserver.vo.SocketMessage;
import com.example.chatserver.vo.UserInfo;
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
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/*
文件创建于  2022/09/20  23：10
*/
//这里注释可以将目前的类定义成一个websocket服务器端
@ServerEndpoint(value = "/chat", configurator = GetHttpSessionConfigurator.class)
@Component
public class ChatEndpoint {
    /**
     * @Autowired
     * private UserServiceImpl userService;
     * Spring管理采用单例模式（singleton），而 WebSocket 是多对象的，即每个客户端对应后台的一个 WebSocket 对象，也可以理解成 new 了一个 WebSocket，这样当然是不能获得自动注入的对象了，因为这两者刚好冲突。
     * @Autowired 注解注入对象操作是在启动时执行的，而不是在使用时，而 WebSocket 是只有连接使用时才实例化对象，且有多个连接就有多个对象。
     * 所以我们可以得出结论，这个 Service 根本就没有注入到 WebSocket 当中。
     *
     * 将需要注入的 Service 改为静态，让它属于当前类，然后通过 setter 方法进行注入即可解决。
     * 注意 setter 方法上不能有 static 关键字
     */

    private static UserServiceImpl userService;
    @Autowired
    public void setUserService(UserServiceImpl userService) {
        ChatEndpoint.userService = userService;
    }

    private static NewsServiceImpl newsService;
    @Autowired
    public void setNewsService(NewsServiceImpl newsService) {
        ChatEndpoint.newsService = newsService;
    }

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
    public void onOpen(Session session, EndpointConfig endpointConfig) throws JsonProcessingException {
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

        System.out.println(userName);

        //getTimeString

        broadcastSystemMessage(userName+"上线","success");
        //群发当前所有在线用户信息
        //SocketMessage socketMessage=new SocketMessage("getOnlineUsersInfo",getOnlineUsersInfo());
        //this.send(socketMessage.toJSONString());
        sendMessage(getOnlineUsersInfo(),"getOnlineUsersInfo");
        LOGGER.info(userName+"上线");

    }

    /**
     * @param message 广播系统消息
     * @return void
     */
    private void broadcastSystemMessage(String message,String type) {
        //所有登录用户名称
        Set<String> names = onlineUsers.keySet();

        for (String name : names) {
            ChatEndpoint chatEndpoint = onlineUsers.get(name);
            chatEndpoint.sendSystemMessage(message,type);
        }
    }

    /**
     * 广播消息
     * @param message
     * @param event
     */
    private void broadcastMessage(String message,String event) {
        Set<String> names = onlineUsers.keySet();
        for (String name : names) {
            ChatEndpoint chatEndpoint = onlineUsers.get(name);
            chatEndpoint.sendMessage(message,event);

        }
    }
    /**关闭时调用*/
    @OnClose
    public void onClose(Session session) {
        //更新用户最后登录时间
        userService.updateLastLoginTime(userName);

        //需要移除下线的用户
        onlineUsers.remove(userName);
        LOGGER.info(userName+"下线");
        //广播
        broadcastSystemMessage(userName+"下线","error");
    }

    /**
     * 接收到客户端发送的数据时调用.
     * @param message 客户端发送的数据
     */
    @OnMessage
    public void onMessage(String message) {
        //System.out.println(message);
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

        resMsg.setEvent(event);
        // TODO 这里可以用反射来改造，会少很多代码，也会好使用
        if(event.equals("getOnlineUsersInfo")){
            resMsg.setMapData(getOnlineUsersInfo());
        }else if(event.equals("newsInsert")){
            //插入新消息
            resMsg.setStringData(newsInsert(msg.getData()).toString());
        }else if(event.equals("imageNewsInsert")){
            resMsg.setStringData(imageNewsInsert(msg.getData()).toString());
        }else{
            //这里没有什么事件就发送什么事件，也可以 发 error 事件

            Map<String,Object> map=new HashMap<>();
            map.put("msg","错误，服务器没有接受这个事件的方法");
            resMsg.setMapData(map);
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
                socketMessage.getMapData().put("message","你已经被挤占下线");
                socketMessage.getMapData().put("type","error");
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

    /**
     * 获取所有在线用户信息
     * @return
     */
    private Map<String,Object> getOnlineUsersInfo(){

        //System.out.println(userService);
        //所有登录用户名称
        Set<String> names = onlineUsers.keySet();
        List<UserInfo> OnlineUsers=new ArrayList<>();
        for (String name : names){
            //查出用户的所有信息
            User user=userService.loadByName(name);
            UserInfo userInfo=new UserInfo(user);
            userInfo.setLastChat("最后的聊天记录还没写");
            OnlineUsers.add(userInfo);
        }
        Map<String,Object> map=new HashMap<>();
        map.put("data",OnlineUsers);
        return map;
    }

    /**
     * 发送系统消息，注意这个函数会自动打上event：system
     * @param message
     * @param type 'success' | 'warning' | 'info' | 'error'
     */
    private void sendSystemMessage(String message,String type){
        SocketMessage socketMessage=new SocketMessage("system",new HashMap<>());
        socketMessage.getMapData().put("message",message);
        socketMessage.getMapData().put("type",type);
        this.send(socketMessage.toJSONString());
    }

    /**
     * 发送消息
     * @param message
     * @param event
     */
    private void sendMessage(String message,String event){
        SocketMessage socketMessage=new SocketMessage(event,message);
        this.send(socketMessage.toJSONString());
    }
    /**
     * 发送消息 (方法重载下)
     * @param message
     * @param event
     */
    private void sendMessage(Map<String,Object> message,String event){
        SocketMessage socketMessage=new SocketMessage(event,message);
        this.send(socketMessage.toJSONString());
    }

    /**
     * 发送消息,根据用户名从在线用户中找到并发送
     * @param userName
     * @param message
     * @param event
     */
    private Boolean sendMessageByUserName(String userName,Map<String,Object> message,String event){
        ChatEndpoint chatEndpoint=this.onlineUsers.get(userName);
        if(chatEndpoint!=null){
            chatEndpoint.sendMessage(message,event);
            return true;
        }
        return false;
    }
    /**
     * 新消息插入
     * @param map
     */
    private Boolean newsInsert(Map<String,Object> map){

        //设置消息发送的时间
        Timestamp time= new Timestamp(System.currentTimeMillis());//获取系统当前时间
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String timeStr = df.format(time);
        time = Timestamp.valueOf(timeStr);
        map.put("time",time.toString());

        News news=(News)Tool.MapToObject(map,News.class);

        //System.out.println(news.getMessage());
        //long r= 0;
        long r= newsService.insert(news);
        if(r==1l){
            //这里 推送到对方 必须在插入对话后，不然会导致 调用 getNewsBySenderIdAndSentId 获取不到新插入的对话
            //推送到对方
            sendMessageByUserName(news.getSentName(),map,"news");
            //this.sendSystemMessage("成功，服务器服务器成功插入了对话","success");
            return true;
        }
        //this.sendSystemMessage("错误，服务器服务器插入失败","error");
        return false;
    }

    /**
     * 插入图片消息   (文件插入也用这个)
     * @param map
     * @return
     */
    private Boolean imageNewsInsert(Map<String,Object> map){
        //设置消息发送的时间
        Timestamp time= new Timestamp(System.currentTimeMillis());//获取系统当前时间
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String timeStr = df.format(time);
        time = Timestamp.valueOf(timeStr);
        map.put("time",time.toString());
        News news=(News)Tool.MapToObject(map,News.class);
        String physicalPath=news.getMessage();
        String fileName= physicalPath.substring(physicalPath.lastIndexOf("\\")+1);

        //移动图片
        if(news.getMessageType().equals("file")){
            if(Tool.moveFileToTarget(news.getMessage(),"D:\\迅雷下载\\my-chat项目图片文件夹\\file\\"+fileName)){
                news.setMessage("http://localhost:19091/static/file/"+fileName);
                long r= newsService.insert(news);
                if(r==1l){
                    sendMessageByUserName(news.getSentName(),map,"news");
                    return true;
                }
            }
        }//移动图片
        else{
            if(Tool.moveFileToTarget(news.getMessage(),"D:\\迅雷下载\\my-chat项目图片文件夹\\images\\"+fileName)){
                news.setMessage("http://localhost:19091/static/images/"+fileName);
                long r= newsService.insert(news);
                if(r==1l){
                    sendMessageByUserName(news.getSentName(),map,"news");
                    return true;
                }
            }
        }
        return false;
    }
}
