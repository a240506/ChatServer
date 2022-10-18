package com.example.chatserver.vo;

import lombok.Data;

/*
文件创建于  2022/10/18  08：29
*/
@Data
public class RegisterParen extends LoginParam {
    private String originalFileName;
    private String physicalPath;
    private String avatarUrl;
}
