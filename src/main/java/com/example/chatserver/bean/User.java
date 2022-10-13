package com.example.chatserver.bean;

/*
文件创建于  2022/09/17  15：32
*/

import com.example.chatserver.common.enumType.Sex;
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
    private String nickname;
    private String email;
    private String province;
    private String city;
    private String town;
    private Sex sex;
    private String bgImage;
    private String signUpTime;
    private Long age;
    private String lastLoginTime;
    private String friendFenzu;
    private String onlineTime;
}
