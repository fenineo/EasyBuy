package com.example.easybuy.mapper;

import com.example.easybuy.entity.UserAddress;

import java.util.List;

public interface UserAddressMapper {
    List<UserAddress> findByUserId(int userId);
    int addUserAddress(UserAddress userAddress);//添加地址
    int removeUserAddress(int id);//删除新闻
    UserAddress findById(int id);//根据ID查询
    int modifyUserAddress(UserAddress userAddress);//修改新闻
    int isDefault(int id);//设置默认地址
    int allisDefault(int userId);//重置默认地址

}
