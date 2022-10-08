package com.example.chatserver.bean;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/*
文件创建于  2022/10/08  16：47
*/
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@Data
public class PYQComments {
    private Long _id;
    private String content;
    private Long parentId;
    private Long level;
    private Long pyqId;
    private Long userId;
    private String createDate;
}
