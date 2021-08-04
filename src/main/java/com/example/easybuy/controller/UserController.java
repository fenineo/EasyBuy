package com.example.easybuy.controller;

import com.example.easybuy.entity.User;
import com.example.easybuy.service.UserService;
import com.example.easybuy.tools.JwtTool;
import com.example.easybuy.tools.MD5Util;
import com.example.easybuy.tools.PageBeanAll;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/user")
@Api(tags = "用户层Controller",description = "操作用户数据")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private RedisTemplate redisTemplate;

    @ApiOperation("查询登录名是否已经存在")
    @RequestMapping(value = "/tourist/existLoginName",method = RequestMethod.POST)
    public String existLoginName(String loginName){
        boolean flag = false;
        if(userService.findByLoginName(loginName) != null){
            flag = true;
        }
        return flag+"";
    }

    @ApiOperation("用户注册接口")
    @RequestMapping(value = "/tourist/register",method = RequestMethod.POST)
    public String register(String loginName, String userName, String password, int sex,
                           @RequestParam(required = false) String identityCode,
                           @RequestParam(required = false) String email,
                           @RequestParam(required = false) String mobile){
        boolean flag = false;
        String _password = MD5Util.md5Hex(password);
        User user = new User(0,loginName,userName,_password,sex,identityCode,email,mobile,0);
        flag = userService.addUser(user);
        return flag+"";
    }

    @ApiOperation("用户登陆接口")
    @RequestMapping(value = "/tourist/login",method = RequestMethod.POST)
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
            if(user.getPassword().equals(MD5Util.md5Hex(password))){
                String token = JwtTool.budilJwt(user);//生成登陆用户对应的token
                //将用户对象存放到redis，设置过期时间为30分钟
                redisTemplate.opsForValue().set(token,user, Duration.ofMinutes(30L));
                System.out.println("token是否存在"+redisTemplate.hasKey(token));
                System.out.println(token);
                System.out.println("通过token获取到的对象"+redisTemplate.opsForValue().get(token).toString());
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

    @ApiOperation("获取登陆用户的信息")
    @RequestMapping(value = "/loginInfo",method = RequestMethod.POST)
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

    @ApiOperation("获取用户列表")
    @RequestMapping(value = "/userList",method = RequestMethod.POST)
    public HashMap<String,Object> userList(HttpServletRequest request,int pageIndex){
        String token = request.getHeader("token");

        int totalCount = userService.findUserCount();
        List<User> userList = userService.findUserPage(pageIndex,10);
        PageBeanAll userPage = new PageBeanAll(pageIndex,10,totalCount);
        userPage.setList(userList);

        HashMap<String,Object> map = new HashMap<>();               //返回给前端的数据集合
        map.put("loginName",JwtTool.parseMap(token).get("loginName"));
        map.put("userPage",userPage);
        return map;
    }

    @ApiOperation("管理员添加用户")
    @RequestMapping(value = "/userAdd",method = RequestMethod.POST)
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

    @ApiOperation("管理员删除用户")
    @RequestMapping(value = "/userRemove",method = RequestMethod.POST)
    public String userRemove(int id){
        boolean flag = userService.removeUser(id);

        return flag+"";
    }

    @ApiOperation("修改用户信息")
    @RequestMapping(value = "/userModify",method = RequestMethod.POST)
    public String userModify(int id, String userName, int sex,
                             @RequestParam(required = false) String password,
                             @RequestParam(required = false) String identityCode,
                             @RequestParam(required = false) String email,
                             @RequestParam(required = false) String mobile,
                             int type){
        boolean flag = false;

        User user = new User(id,null,userName,password,sex,identityCode,email,mobile,type);
        flag = userService.modifyUser(user);

        return flag+"";
    }

    @ApiOperation("获取登陆用户的信息")
    @RequestMapping(value = "/userInfoByToken",method = RequestMethod.POST)
    public User userInfoByToken(HttpServletRequest request){
        String token = request.getHeader("token");
        User user = userService.findByLoginName(JwtTool.parseMap(token).get("loginName")+"");
        return user;
    }
    //根据登陆名获取用户
//    @RequestMapping("/userByLoginName")
//    public User userByLoginName(String loginName){
//        User user = userService.findByLoginName(loginName);
//        return user;
//    }
}
