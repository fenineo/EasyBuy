package com.example.easybuy.controller;

import com.example.easybuy.entity.User;
import com.example.easybuy.service.UserService;
import com.example.easybuy.tools.JwtTool;
import com.example.easybuy.tools.PageBeanAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("/tourist/existLoginName")
    //查询登录名(User.loginName)是否已经存在
    public String existLoginName(String loginName){
        boolean flag = false;
        if(userService.findByLoginName(loginName) != null){
            flag = true;
        }
        return flag+"";
    }

    @RequestMapping("/tourist/register")
    //用户注册
    public String register(String loginName, String userName, String password, int sex,
                           @RequestParam(required = false) String identityCode,
                           @RequestParam(required = false) String email,
                           @RequestParam(required = false) String mobile){
        boolean flag = false;
        User user = new User(0,loginName,userName,password,sex,identityCode,email,mobile,0);
        flag = userService.addUser(user);
        return flag+"";
    }

    @RequestMapping("/tourist/login")
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
    //获取登陆用户的信息
    @RequestMapping("/loginInfo")
    public HashMap<String,Object> loginInfo(HttpServletRequest request){
        String token = request.getHeader("token");
        boolean flag = false;
        HashMap<String,Object> map = new HashMap<>();

        if (JwtTool.parseJwt(token)){
            map = JwtTool.parseMap(token);
            flag = true;
        }

        map.put("flag",flag);
        return map;
    }
    //获得用户列表
    @RequestMapping("/userList")
    public PageBeanAll userList(int pageIndex){
        int totalCount = userService.findUserCount();
        List<User> userList = userService.findUserPage(pageIndex,10);
        PageBeanAll userPage = new PageBeanAll(pageIndex,10,totalCount);
        userPage.setList(userList);
        return userPage;
    }
    //管理员添加用户
    @RequestMapping("/userAdd")
    public String userAdd(String loginName, String userName, String password, int sex,
                          @RequestParam(required = false) String identityCode,
                          @RequestParam(required = false) String email,
                          @RequestParam(required = false) String mobile,
                          int type){
        boolean flag = false;

        User user = new User(0,loginName,userName,password,sex,identityCode,email,mobile,type);
        flag = userService.addUser(user);

        return flag+"";
    }
    //管理员删除用户
    @RequestMapping("/userRemove")
    public String userRemove(int id){
        boolean flag = userService.removeUser(id);

        return flag+"";
    }
    //修改用户信息
    @RequestMapping("/userModify")
    public String userModify(int id, String loginName, String userName, int sex,
                             @RequestParam(required = false) String identityCode,
                             @RequestParam(required = false) String email,
                             @RequestParam(required = false) String mobile,
                             int type){
        boolean flag = false;

        User user = new User(id,loginName,userName,null,sex,identityCode,email,mobile,type);
        flag = userService.modifyUser(user);

        return flag+"";
    }
    //根据登陆名获取用户
//    @RequestMapping("/userByLoginName")
//    public User userByLoginName(String loginName){
//        User user = userService.findByLoginName(loginName);
//        return user;
//    }
}
