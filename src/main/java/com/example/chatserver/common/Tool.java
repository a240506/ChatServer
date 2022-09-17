package com.example.chatserver.common;

import java.util.HashMap;
import java.util.Map;

/*
文件创建于  2022/09/17  15：04
*/
public class Tool {
    public static R result(Object data, int n, String msg){
        Map<String,Object> meta = new HashMap<String,Object>();
        meta.put("status",n);
        meta.put("msg",msg);
        return R.ok().put("data", data).put("meta",meta);
    }
}