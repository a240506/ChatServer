package com.example.chatserver.bean;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Time;

/*
文件创建于  2022/10/03  23：23
*/
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@Data
public class News {
    private Long _id;
    private Long senderId;
    private String senderName;
    private String senderAvatar;
    private Time time;
    private Long sentId;
    private String sentName;
    private String message;
    private String messageType;
}
