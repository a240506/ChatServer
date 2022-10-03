package com.example.chatserver.bean;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/*
文件创建于  2022/10/03  23：27
*/
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@Data
public class Friendlies {
    private Long _id;
    private Long userX;
    private String userY;
}
