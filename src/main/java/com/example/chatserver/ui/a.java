package com.example.chatserver.ui;


import com.example.chatserver.common.R;
import com.example.chatserver.common.Tool;
import com.example.chatserver.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/*
文件创建于  2022/09/17  13：01
*/
@RestController
public class a {
    @Autowired
    private UserServiceImpl userService;

    @GetMapping(value = "/toChatroom")
    public String toChatroom() {
        return "chat";
    }

    @RequestMapping( value = "/goods",method = RequestMethod.POST)
    public R getRoles(@RequestBody Map<String, Object> params){
        Map<String,Object> meta = new HashMap<String,Object>();

        meta.put("a",userService.loadByName("test"));


        return Tool.result(meta,200,"获取成功");
    }
}
