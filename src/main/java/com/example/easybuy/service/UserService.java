package com.example.easybuy.service;

import com.example.easybuy.entity.User;

import java.util.List;

public interface UserService {
    boolean addUser(User user);
    boolean modifyUser(User user);
    boolean removeUser(int id);
    List<User> findUserList();
    List<User> findUserPage(int pageIndex,int pageSize);
    int findUserCount();
    User findById(int id);
    User findByLoginName(String loginName);
}
