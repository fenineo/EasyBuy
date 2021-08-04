package com.example.easybuy.service;

import com.example.easybuy.entity.UserAddress;

import java.util.List;

public interface UserAddressService {
    List<UserAddress> findByUserId(int userId);
    boolean addUserAddress(UserAddress userAddress);//添加地址
    boolean removeUserAddress(int id);//删除新闻
    UserAddress findById(int id);//根据ID查询
    boolean modifyUserAddress(UserAddress userAddress);//修改新闻
    boolean isDefault(int id);//设置默认地址
    boolean allisDefault(int userId);//重置默认地址
    UserAddress findByUser(int userId);//根据用户id查询默认地址
}
