package com.example.chatserver.bean;

/*
文件创建于  2022/09/17  15：32
*/

import lombok.*;

//自动实现Equals和HashCode方法
@EqualsAndHashCode
//自动实现ToString方法
@ToString
//自动实现无参构造
@NoArgsConstructor
//自动实现get set
@Data
public class User {
    private Long userId;
    private String userName;
    private String password;
    private String type;
    private String avatarUrl;
}
