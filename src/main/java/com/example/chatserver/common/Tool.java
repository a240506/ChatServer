package com.example.chatserver.common;

import com.example.chatserver.vo.SocketMessage;
import com.example.chatserver.ws.ChatEndpoint;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;


import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

/*
文件创建于  2022/09/17  15：04
*/
public class Tool {
    private static final ObjectMapper JSON = new ObjectMapper();
    private static final String SALT="fang";
    private final static Logger LOGGER = LogManager.getLogger(Tool.class);
    public static R result(Object data, int n, String msg){
        Map<String,Object> meta = new HashMap<String,Object>();
        meta.put("status",n);
        meta.put("msg",msg);
        return R.ok().put("data", data).put("meta",meta);
    }


    //Map转Object

    /**
     * Map转Object,enum类型不会转换
     * @param map
     * @param beanClass
     * @return
     */
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
    public static String getTimeString(String format){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
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

    /**
     * 获取指定文件夹下的文件，（没有递归）
     * @param dirFile
     * @return
     */
    public static List<File> getAllFile(File dirFile) {
        // 如果文件夹不存在或着不是文件夹，则返回 null
        if (Objects.isNull(dirFile) || !dirFile.exists() || dirFile.isFile())
            return null;

        File[] childrenFiles = dirFile.listFiles();
        if (Objects.isNull(childrenFiles) || childrenFiles.length == 0)
            return null;

        List<File> files = new ArrayList<>();
        for (File childFile : childrenFiles) {
            // 如果是文件，直接添加到结果集合
            if (childFile.isFile()) {
                files.add(childFile);
            }
            //以下几行代码取消注释后可以将所有子文件夹里的文件也获取到列表里
//            else {
//                // 如果是文件夹，则将其内部文件添加进结果集合
//                List<File> cFiles = getAllFile(childFile);
//                if (Objects.isNull(cFiles) || cFiles.isEmpty()) continue;
//                files.addAll(cFiles);
//            }
        }
        return files;
    }

    /**
     * 文件下载
     * @param urlString
     * @param savePath
     * @param filename
     * @throws IOException
     */
    public static void downloadFile(String urlString, String savePath, String filename) throws IOException {
        // 构造URL
        URL url = new URL(urlString);
        // 打开连接
        URLConnection con = url.openConnection();
        //设置请求超时为20s
        con.setConnectTimeout(20 * 1000);
        //文件路径不存在 则创建
        File sf = new File(savePath);
        if (!sf.exists()) {
            sf.mkdirs();
        }
        //jdk 1.7 新特性自动关闭
        try (InputStream in = con.getInputStream();
             OutputStream out = new FileOutputStream(sf.getPath() + "\\" + filename)) {
            //创建缓冲区
            byte[] buff = new byte[1024];
            int n;
            // 开始读取
            while ((n = in.read(buff)) >= 0) {
                out.write(buff, 0, n);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * md5加密 ,默认加盐
     * @param data
     * @return
     * @throws Exception
     */
    public static String decryMD5(String data) throws Exception {
        data+=SALT;
        MessageDigest md5 = MessageDigest.getInstance("MD5");

        md5.update(data.getBytes());

        return new String(md5.digest()) ;
    }

    /**
     * 类型转换
     * @param n
     * @return
     */
    public static int Long2Int(Long n)  {
        return Integer.parseInt(n.toString());
    }

    public static String selfMoveFile(String physicalPath){

        String filename=physicalPath.substring(physicalPath.lastIndexOf("\\")+1);

        if(Tool.moveFileToTarget(physicalPath,"D:\\迅雷下载\\my-chat项目图片文件夹\\images\\"+filename)){
            return "http://localhost:19091/static/images/"+filename;

        }
        return "";
    }

}