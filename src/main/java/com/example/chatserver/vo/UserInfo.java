package com.example.chatserver.vo;

import com.example.chatserver.bean.User;
import lombok.Data;

/*
文件创建于  2022/10/01  12：19
*/
//用户信息
@Data
public class UserInfo {
    private Long UserId;
    private String userName;
    //头像地址
    private String avatarUrl;
    //和要聊天的用户的 上次一次聊天 的最后一句话
    private String lastChat;

    public UserInfo(User user) {
        UserId = user.getUserId();
        this.userName = user.getUserName();
        this.avatarUrl = user.getAvatarUrl();
    }
}
