package com.example.chatserver.serve;

import com.example.chatserver.bean.User;
import com.example.chatserver.common.R;
import com.example.chatserver.common.Tool;
import com.example.chatserver.service.impl.UserServiceImpl;
import com.example.chatserver.vo.LoginParam;
import com.wf.captcha.ArithmeticCaptcha;
import com.wf.captcha.SpecCaptcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


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
    public R getRoles(@RequestBody LoginParam params){
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
        return Tool.result(user,0,"登录失败");
    }

    @RequestMapping("/hello")
    public void  captcha(HttpServletResponse response) throws IOException {

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

        SpecCaptcha captcha = new SpecCaptcha(130, 48);
        String text = captcha.text();// 获取验证码的字符
        char[] chars = captcha.textChar();// 获取验证码的字符数组

        System.out.println("验证码："+text);
        System.out.println(chars);
        // 输出验证码
        captcha.out(response.getOutputStream());


        //return Result.success("图片验证码", map);
    }

}
