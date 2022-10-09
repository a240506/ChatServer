package com.example.chatserver.common;

import com.example.chatserver.vo.SocketMessage;
import com.example.chatserver.ws.ChatEndpoint;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;


import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

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

    public static String getTimeString(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));//设置北京时间

        return simpleDateFormat.format(new Date()).toString();
    }
    /**
     * 	移动文件到指定位置
     * @param fileFullNameCurrent 要移动的文件全路径
     * @param fileFullNameTarget 移动到目标位置的文件全路径
     * @return 是否移动成功， true：成功；否则失败
     */
    public static Boolean moveFileToTarget(String fileFullNameCurrent,String fileFullNameTarget) 		{
        boolean ismove = false;

        File oldName = new File(fileFullNameCurrent);

        if (!oldName.exists()) {
            LOGGER.warn("{}","要移动的文件不存在！");
            return ismove;
        }

        if (oldName.isDirectory()) {
            LOGGER.warn("{}","要移动的文件是目录，不移动！");
            return false;
        }

        File newName = new File(fileFullNameTarget);

        if (newName.isDirectory()) {
            LOGGER.warn("{}","移动到目标位置的文件是目录，不能移动！");
            return false;
        }

        String pfile = newName.getParent();
        File pdir = new File(pfile);

        if (!pdir.exists()) {
            pdir.mkdirs();
            LOGGER.warn("{}","要移动到目标位置文件的父目录不存在，创建：" + pfile);
        }

        ismove = oldName.renameTo(newName);
        return ismove;
    }


}