package com.example.chatserver.serve;

import com.example.chatserver.bean.News;
import com.example.chatserver.service.impl.NewsServiceImpl;
import com.example.chatserver.service.impl.UserServiceImpl;
import com.example.chatserver.vo.LoginParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

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
        Timestamp time= new Timestamp(System.currentTimeMillis());//获取系统当前时间
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String timeStr = df.format(time);
        time = Timestamp.valueOf(timeStr);
        news.setTime(time.toString());
        newsService.insert(news);
        return "111";
    }
    //使用 @RequestBody 必须要post
    @RequestMapping( value = "/getNewsBySenderIdAndSentId")
    public List<News> getNewsBySenderIdAndSentId(@RequestBody Map<String, Object> params) {
        System.out.println();
        return newsService.newsBySenderIdAndSentId((int)params.get("senderId"),(int)params.get("sentId"));
    }
}
