package com.example.chatserver.bean;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Time;
import java.sql.Timestamp;

/*
文件创建于  2022/10/03  23：23
*/
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@Data
public class News {
    private Long _id;
    //这里写Long 类型会导致Tool的MapToObject 转换错误
    private int senderId;
    private String senderName;
    private String senderAvatar;
    private String time;
    private int sentId;
    private String sentName;
    private String message;
    private String messageType;
}
