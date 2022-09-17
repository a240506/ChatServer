package com.example.chatserver.serve;

import com.example.chatserver.common.R;
import com.example.chatserver.common.Tool;
import com.example.chatserver.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/*
文件创建于  2022/09/17  17：01
*/
@RestController
public class LoginServe {

    @Autowired
    private UserServiceImpl userService;

    @RequestMapping( value = "/login",method = RequestMethod.POST)
    public R getRoles(@RequestBody Map<String, Object> params){
        Map<String,Object> meta = new HashMap<String,Object>();

        meta.put("a",userService.loadByName("test"));

        return Tool.result(meta,200,"获取成功");
    }
}
