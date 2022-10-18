package com.example.chatserver.bean;

import com.example.chatserver.common.enumType.MessageType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/*
文件创建于  2022/10/18  15：50
*/
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@Data
public class GroupNews {
    private int _id;
    private String createDate;
    private int senderId;
    private String senderUserName;
    private String senderAvatar;
    private String message;
    private MessageType messageType;
    private int groupId;
}
