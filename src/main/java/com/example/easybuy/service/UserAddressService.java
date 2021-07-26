package com.example.easybuy.service;

import com.example.easybuy.entity.UserAddress;

import java.util.List;

public interface UserAddressService {

    List<UserAddress> findByUserId(int userId);
}
