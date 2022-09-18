package com.example.chatserver.serve;

import com.example.chatserver.bean.User;
import com.example.chatserver.common.R;
import com.example.chatserver.common.Tool;
import com.example.chatserver.service.impl.UserServiceImpl;
import com.example.chatserver.vo.LoginParam;
import com.wf.captcha.ArithmeticCaptcha;
import com.wf.captcha.ChineseCaptcha;
import com.wf.captcha.GifCaptcha;
import com.wf.captcha.SpecCaptcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/*
文件创建于  2022/09/17  17：01
*/
@RestController
public class LoginServe {

    @Autowired
    private UserServiceImpl userService;


    @RequestMapping( value = "/login",method = RequestMethod.POST)
    public R getRoles(HttpServletRequest request,@RequestBody LoginParam params){
        String  captcha=(String)request.getSession().getAttribute("captcha");
        System.out.println(captcha);

        //判断验证码是否正确
        if(!params.getVcCode().equals(captcha)){
            return Tool.result(null,0,"登录失败,验证码错误");
        }
        //Map<String,Object> meta = new HashMap<String,Object>();
        User user=null;
        //用户登录
        if(params.getType().equals("user")){
            user= userService.userLogin(params.getUserName(),params.getPassword());
        //    管理员登录
        }else if(params.getType().equals("admin")){
            user= userService.adminLogin(params.getUserName(),params.getPassword());
        }

        if(user!=null){
        //    登录成功
            return Tool.result(user,200,"登录成功");
        }
        return Tool.result(null,0,"登录失败,账号或者密码错误");
    }
    //获取验证码
    //链接后的数字是随机数，保证不会被浏览器缓存，没有其他用途
    @RequestMapping("/captcha/{random}")
    public void  captcha(HttpServletRequest request, HttpServletResponse response, @PathVariable String random) throws IOException {
        //算术类型
        //ArithmeticCaptcha captcha = new ArithmeticCaptcha();
        //中文类型验证吗
        //ChineseCaptcha captcha = new ChineseCaptcha();
        // 英文与数字验证码
        // SpecCaptcha captcha = new SpecCaptcha();
        //英文与数字动态验证码
        //GifCaptcha captcha = new GifCaptcha();
        //中文动态验证码
        //ChineseGifCaptcha captcha = new ChineseGifCaptcha();
        //几位数运算   默认是两位
        //captcha.setLen(2);

        ArithmeticCaptcha captcha = new ArithmeticCaptcha(80, 30);
        String text = captcha.text();// 获取验证码的字符
        //保存验证码到服务器session中
        request.getSession().setAttribute("captcha",text);
        System.out.println("验证码："+text);
        // 输出验证码
        captcha.out(response.getOutputStream());

    }
    @RequestMapping("/hello")
    public String  hello(HttpServletRequest request, HttpServletResponse response) {
        String  captcha=(String)request.getSession().getAttribute("captcha");
        System.out.println(captcha);

        return captcha;
    }


}
