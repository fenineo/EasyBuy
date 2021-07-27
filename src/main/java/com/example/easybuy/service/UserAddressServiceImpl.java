package com.example.easybuy.service;

import com.example.easybuy.entity.UserAddress;
import com.example.easybuy.mapper.UserAddressMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserAddressServiceImpl implements UserAddressService{
    @Resource
    private UserAddressMapper userAddressMapper;

    @Override
    public List<UserAddress> findByUserId(int userId) {
        List<UserAddress> list=userAddressMapper.findByUserId(userId);
        return list;
    }

    @Override
    public boolean addUserAddress(UserAddress userAddress) {
        boolean flag = false;
        if (userAddressMapper.addUserAddress(userAddress) > 0){
            flag = true;
        }
        return flag;
    }

    @Override
    public boolean removeUserAddress(int id) {
        boolean flag = false;
        if (userAddressMapper.removeUserAddress(id) > 0){
            flag = true;
        }
        return flag;
    }

    @Override
    public UserAddress findById(int id) {
        UserAddress userAddress=userAddressMapper.findById(id);
        return userAddress;
    }

    @Override
    public boolean modifyUserAddress(UserAddress userAddress) {
        boolean flag = false;
        if (userAddressMapper.modifyUserAddress(userAddress) > 0){
            flag = true;
        }
        return flag;
    }

    @Override
    public boolean isDefault(int id) {
        boolean flag = false;
        if (userAddressMapper.isDefault(id)> 0){
            flag = true;
        }
        return flag;
    }

    @Override
    public boolean allisDefault(int userId) {
        boolean flag = false;
        if (userAddressMapper.allisDefault(userId)> 0){
            flag = true;
        }
        return flag;
    }
}
