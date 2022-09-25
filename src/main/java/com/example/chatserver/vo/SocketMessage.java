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
    private Map<String,Object> data;
    public String toJSONString(){
        ObjectMapper objectMapper = new ObjectMapper();
        //把数据转换成json字符串
        String JSONString=null;
        try {
            JSONString=objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            LOGGER.error("数据转换成json字符串失败,数据为:"+this.toString(), e);
            e.printStackTrace();
        }
        return JSONString;
    }
}
