package com.example.easybuy.service;

import com.example.easybuy.entity.User;
import com.example.easybuy.mapper.UserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl implements UserService{
    @Resource
    private UserMapper userMapper;

    @Override
    public boolean addUser(User user) {
        boolean flag = false;
        if (userMapper.addUser(user) > 0){
            flag = true;
        }
        return flag;
    }

    @Override
    public boolean modifyUser(User user) {
        boolean flag = false;
        if (userMapper.modifyUser(user) > 0){
            flag = true;
        }
        return flag;
    }

    @Override
    public boolean removeUser(int id) {
        boolean flag = false;
        if (userMapper.removeUser(id) > 0){
            flag = true;
        }
        return flag;
    }

    @Override
    public List<User> findUserList() {
        return userMapper.findUserList();
    }

    @Override
    public User findById(int id) {
        return userMapper.findById(id);
    }
}
