package com.example.chatserver.bean;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Time;

/*
文件创建于  2022/10/03  23：28
*/
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@Data
public class Blogs {
    private Long _id;
    private String tags;
    private String content;
    private String desc;
    private Long authorId;
    private String title;
    private Time createDate;
    private Time updateDate;
    private String authorName;
}
