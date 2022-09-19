package com.example.chatserver.dao;

public interface BaseDao <T> {

    T loadByName(String name);
    Long insert(T bean);
}
