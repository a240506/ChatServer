package com.example.chatserver.vo;

import com.example.chatserver.bean.Groups;
import lombok.Data;
import lombok.ToString;

/*
文件创建于  2022/10/18  21：17
*/
@ToString
@Data
public class CreateGroupsParam extends Groups {
    private String originalFileName;
    private String physicalPath;
}
