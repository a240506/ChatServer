package com.example.chatserver.bean;

import com.example.chatserver.common.enumType.MessageType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/*
文件创建于  2022/10/18  15：55
*/
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@Data
public class GroupUsers {
    private int _id;
    private String manager;
    private String holder;
    private int groupId;
    private int userId;
    private String userName;
    private String createDate;
}
