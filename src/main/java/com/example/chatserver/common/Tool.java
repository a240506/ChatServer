package com.example.chatserver.common;

import com.example.chatserver.vo.SocketMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.BeanUtils;


import java.lang.reflect.InvocationTargetException;
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

    public static <T> T mapToBean(Map<String, Object> map, Class<T> class1) {

        T bean = null;

        try {

            bean = class1.newInstance();
            //BeanUtils.copyProperties();
            BeanUtils.copyProperties(map, bean);
            //BeanUtils.
        } catch (InstantiationException e) {

            e.printStackTrace();

        } catch (IllegalAccessException e) {

            e.printStackTrace();

        }

        return bean;

    }



}