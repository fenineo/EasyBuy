package com.example.easybuy.controller;

import com.example.easybuy.entity.User;
import com.example.easybuy.service.UserService;
import com.example.easybuy.tools.JwtTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("/existLoginName")
    //查询登录名(User.loginName)是否已经存在
    public String existLoginName(String loginName){
        boolean flag = false;
        if(userService.findByLoginName(loginName) != null){
            flag = true;
        }
        return flag+"";
    }

    @RequestMapping("/register")
    //用户注册
    public String register(String loginName, String userName, String password, String sex,
                           @RequestParam(required = false) String identityCode,
                           @RequestParam(required = false) String email,
                           @RequestParam(required = false) String mobile,
                           String type){
        boolean flag = false;
        int _sex = Integer.parseInt(sex);
        User user = new User(0,loginName,userName,password,_sex,identityCode,email,mobile,0);
        try {
            if(userService.addUser(user)){
                flag = true;
            }
        }catch (Exception e){
            return flag+"";
        }
        return flag+"";
    }

    @RequestMapping("/login")
    //用户登陆
    public HashMap<String,Object> login(String loginName,String password){
        /**
         * 返回参数
         * flag:登陆成功还是失败
         * token:token字符串
         * prompt:提示信息
         */
        HashMap<String,Object> map = new HashMap<>();
        map.put("flag",false);//默认登陆失败

        User user = userService.findByLoginName(loginName);
        //用户是否存在
        if (user != null){
            //密码是否正确
            if(password.equals(user.getPassword())){
                String token = JwtTool.budilJwt(user);//生成登陆用户对应的token
                map.put("flag",true);
                map.put("token",token);
            }else {
                map.put("prompt","密码错误");
            }
        }else {
            map.put("prompt","用户不存在");
        }
        return map;
    }
    //通过token获取登陆用户的信息
    @RequestMapping("/loginInfo")
    public HashMap<String,Object> loginInfo(String token){
        HashMap<String,Object> map = new HashMap<>();
        map.put("flag",false);

        if (JwtTool.parseJwt(token)){
            map = JwtTool.parseUser(token);
            map.put("flag",true);
        }

        return map;
    }

}
