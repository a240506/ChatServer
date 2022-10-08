package com.example.chatserver.serve;

import com.example.chatserver.bean.News;
import com.example.chatserver.bean.PYQNews;
import com.example.chatserver.service.impl.PYQNewsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

/*
文件创建于  2022/10/08  16：57
*/
@RestController
@RequestMapping("/PYQNews")
public class PYQNewsServe {
    @Autowired
    PYQNewsServiceImpl pyqNewsService;
    @RequestMapping("/insert")
    public String  newsInsert(@RequestBody PYQNews pyqNews) {
        Timestamp time= new Timestamp(System.currentTimeMillis());//获取系统当前时间
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String timeStr = df.format(time);
        time = Timestamp.valueOf(timeStr);
        pyqNews.setCreateDate(time.toString());
        Long l= pyqNewsService.insert(pyqNews);
        if(l==1l){
            return "true";
        }
        return "false";
    }
    @RequestMapping("/getPYQ")
    public List<PYQNews> getPYQ(HttpServletRequest request) {
        String userName=(String)request.getSession().getAttribute("userName");
        //TODO 这里应该是要提取好友列表，获取好友的全部动态,这里少了


        return pyqNewsService.getPYQByName(userName);
    }
}
