package com.example.easybuy.controller;

import com.example.easybuy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("/existLoginName")
    //查询登录名(User.loginName)是否已经存在
    public String existLoginName(String loginName){
        boolean flag = false;
        if(userService.findByLoginName(loginName)){
            flag = true;
        }
        return flag+"";
    }

}
