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
        try {
            if (userMapper.addUser(user) > 0){
                flag = true;
            }
        }catch (Exception e){
            return flag;
        }
        return flag;
    }

    @Override
    public boolean modifyUser(User user) {
        boolean flag = false;
        try {
            if (userMapper.modifyUser(user) > 0){
                flag = true;
            }
        }catch (Exception e){
            return flag;
        }
        return flag;
    }

    @Override
    public boolean removeUser(int id) {
        boolean flag = false;
        try {
            if (userMapper.removeUser(id) > 0){
                flag = true;
            }
        }catch (Exception e){
            return flag;
        }
        return flag;
    }

    @Override
    public List<User> findUserList() {
        return userMapper.findUserList();
    }

    @Override
    public List<User> findUserPage(int pageIndex, int pageSize) {
        //计算分页查询开始位置
        Integer _pageIndex = (pageIndex-1)*pageSize;
        return userMapper.findUserPage(_pageIndex,pageSize);
    }

    @Override
    public int findUserCount() {
        return userMapper.findUserCount();
    }

    @Override
    public User findById(int id) {
        return userMapper.findById(id);
    }

    @Override
    /**
     * 根据登陆名查询用户
     */
    public User findByLoginName(String loginName) {
        return userMapper.findByLoginName(loginName);
    }
}
