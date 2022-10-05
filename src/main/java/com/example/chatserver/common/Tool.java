package com.example.chatserver.common;

import com.example.chatserver.vo.SocketMessage;
import com.example.chatserver.ws.ChatEndpoint;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;


import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

/*
文件创建于  2022/09/17  15：04
*/
public class Tool {
    private static final ObjectMapper JSON = new ObjectMapper();
    private final static Logger LOGGER = LogManager.getLogger(Tool.class);
    public static R result(Object data, int n, String msg){
        Map<String,Object> meta = new HashMap<String,Object>();
        meta.put("status",n);
        meta.put("msg",msg);
        return R.ok().put("data", data).put("meta",meta);
    }


    //Map转Object
    public static Object MapToObject(Map<String, Object> map, Class<?> beanClass) {
        if (map == null)
            return null;
        Object obj = null;
        try {
            obj = beanClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            int mod = field.getModifiers();
            if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
                continue;
            }
            field.setAccessible(true);
            if (map.containsKey(field.getName())) {
                try {
                    field.set(obj, map.get(field.getName()));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return obj;
    }



}