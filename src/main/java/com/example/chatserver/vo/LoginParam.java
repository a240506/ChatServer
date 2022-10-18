package com.example.chatserver.vo;

import lombok.Data;


/*
文件创建于  2022/09/17  22：50
*/
@Data
public class LoginParam {
    private String userName;
    private String password;
    //验证码
    private String vcCode;
    //用来判断 user 和 admin
    private String type;
}
