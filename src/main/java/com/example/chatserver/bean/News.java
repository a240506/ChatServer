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
    /**
     * mysql的utf8编码的一个字符最多3个字节，但是一个emoji表情为4个字节，所以utf8不支持存储emoji表情。
     * 但是utf8的超集utf8mb4一个字符最多能有4字节，所以能支持emoji表情的存储。
     * ALTER DATABASE 数据库名 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci;
     * ALTER TABLE 表名 CONVERT TO CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
     * 加上这两句话解决
     */

    private String message;
    private String messageType;
}
