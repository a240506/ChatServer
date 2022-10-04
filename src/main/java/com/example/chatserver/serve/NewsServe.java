package com.example.chatserver.serve;

import com.example.chatserver.bean.News;
import com.example.chatserver.service.impl.NewsServiceImpl;
import com.example.chatserver.service.impl.UserServiceImpl;
import com.example.chatserver.vo.LoginParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/*
文件创建于  2022/10/04  11：09
*/
@RestController
@RequestMapping("/news")
public class NewsServe {
    @Autowired
    private NewsServiceImpl newsService;
    @RequestMapping("/insert")
    public String  newsInsert(@RequestBody News news) {
        //News news=new News();
        //news.setSenderId(3L);
        //news.setSenderName("test2");
        //news.setSenderAvatar("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimg.woyaogexing.com%2F2016%2F02%2F02%2F6e19342824aec4a6%21200x200.jpg&refer=http%3A%2F%2Fimg.woyaogexing.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1667200831&t=48c8d821c26b2a1433e9674eff42b36a");
        Timestamp time= new Timestamp(System.currentTimeMillis());//获取系统当前时间
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String timeStr = df.format(time);
        time = Timestamp.valueOf(timeStr);
        news.setTime(time.toString());
        //
        //news.setSentId(2L);
        //news.setSentName("test");
        //news.setMessage("测试内容");
        //news.setMessageType("text");
        //
        newsService.insert(news);




        return "111";
    }
}
