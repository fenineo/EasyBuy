package com.example.easybuy.mapper;

import com.example.easybuy.entity.UserAddress;

import java.util.List;

public interface UserAddressMapper {
    List<UserAddress> findByUserId(int userId);
}
