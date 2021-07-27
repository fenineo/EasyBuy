package com.example.easybuy.mapper;

import com.example.easybuy.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {
    int addUser(User user);
    int removeUser(int id);
    int modifyUser(User user);
    List<User> findUserList();
    List<User> findUserPage(@Param("pageIndex") int pageIndex,@Param("pageSize") int pageSize);
    int findUserCount();
    User findById(int id);
    User findByLoginName(String loginName);
}
