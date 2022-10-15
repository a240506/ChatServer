package com.example.chatserver.bean;

import com.example.chatserver.common.enumType.add_message_type;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/*
文件创建于  2022/10/15  13：40
*/
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@Data
public class AddMessage {
    private Long _id;
    private int userX;
    private int userY;
    private add_message_type type;
    private String message;
}
