package com.example.easybuy.controller;

import com.alibaba.fastjson.JSON;
import com.example.easybuy.entity.User;
import com.example.easybuy.entity.UserAddress;
import com.example.easybuy.service.UserAddressService;
import com.example.easybuy.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/UserAddress")
public class UserAddressController {
    @Resource
    private UserAddressService userAddressService;
    @Resource
    private UserService userService;
    @RequestMapping("/findByUserId")
    private String findByUserId(String userId){
        List<UserAddress> list=userAddressService.findByUserId(Integer.parseInt(userId));
        return JSON.toJSONString(list);
    }
}
