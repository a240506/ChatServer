package com.example.chatserver.vo;

import com.example.chatserver.ws.ChatEndpoint;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/*
文件创建于  2022/09/25  12：19
*/

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Data
public class SocketMessage {
    private final static Logger LOGGER = LogManager.getLogger(SocketMessage.class);
    private String event;
    private Map<String,Object> mapData;
    private String stringData;

    public SocketMessage(String event,Map<String,Object> kvHashMap){
        this.event=event;
        this.mapData=kvHashMap;
    }
    public SocketMessage(String event,String stringData){
        this.event=event;
        this.stringData=stringData;
    }

    public String toJSONString(){
        ObjectMapper objectMapper = new ObjectMapper();
        //把数据转换成json字符串
        String JSONString=null;

        Map<String,Object> data=new HashMap<>();
        data.put("event",this.event);
        if(this.stringData!=null){
            data.put("data",this.stringData);
        }else {
            data.put("data",this.mapData);
        }

        try {
            JSONString=objectMapper.writeValueAsString(data);
        } catch (JsonProcessingException e) {
            LOGGER.error("数据转换成json字符串失败,数据为:"+this.toString(), e);
            e.printStackTrace();
        }
        return JSONString;
    }
}
