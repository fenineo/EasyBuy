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
}
