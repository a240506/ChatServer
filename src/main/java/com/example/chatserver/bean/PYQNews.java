package com.example.chatserver.bean;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/*
文件创建于  2022/10/08  16：42
*/
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@Data
public class PYQNews {
    private Long _id;
    private Long readCount;
    private Long likes;
    private Long userId;
    private String userName;
    private String avatarUrl;
    private String content;
    private String createDate;
}
