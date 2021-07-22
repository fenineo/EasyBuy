package com.example.easybuy.mapper;

import com.example.easybuy.entity.User;

import java.util.List;

public interface UserMapper {
    int addUser(User user);
    int removeUser(int id);
    int modifyUser(User user);
    List<User> findUserList();
    User findById(int id);
}
