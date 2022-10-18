package com.example.chatserver.bean;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/*
文件创建于  2022/10/18  15：44
*/
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@Data
public class Groups {
    private int _id;
    //describe,desc,Groups 全是mysql的关键字
    private String des;
    private String avatar;
    private String userNum;
    private String groupsName;
    private int holderUserId;
    private String holderName;
    private String createDate;
}
