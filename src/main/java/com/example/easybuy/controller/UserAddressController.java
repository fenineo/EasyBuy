package com.example.easybuy.controller;

import com.alibaba.fastjson.JSON;
import com.example.easybuy.entity.UserAddress;
import com.example.easybuy.service.UserAddressService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/UserAddress")
@Api(tags = "地址层Controller",description = "操作新闻数据")
public class UserAddressController {
    @Resource
    private UserAddressService userAddressService;
    /**
     * 查询收获地址
     */
    @ApiOperation("查询收货地址")
    @RequestMapping(value = "/findByUserId",method = RequestMethod.GET)
    private String findByUserId(String userId){
        List<UserAddress> list=userAddressService.findByUserId(Integer.parseInt(userId));
        return JSON.toJSONString(list);
    }
    /**
     * 添加收货地址
     */
    @ApiOperation("添加收货地址")
    @RequestMapping(value = "/addUserAddress",method = RequestMethod.GET)
    private String addUserAddress(String userId, String consignee, String address, String phone, String email, String xaddress){
        UserAddress userAddress=new UserAddress(Integer.parseInt(userId),consignee,address,phone,email,xaddress);
        boolean flag=userAddressService.addUserAddress(userAddress);
        return "";
    }
    /**
     * 删除收货地址
     */
    @ApiOperation("删除收货地址")
    @RequestMapping(value = "/removeUserAddress",method = RequestMethod.GET)
    private String removeUserAddress(String id){
        boolean flag=userAddressService.removeUserAddress(Integer.parseInt(id));
        return "";
    }
    /**
     * 根据id查询回显编辑
     * @param id
     * @return
     */
    @ApiOperation("根据ID查询收货地址")
    @RequestMapping(value = "/findById",method = RequestMethod.GET)
    private String findById(String id){
        UserAddress userAddress=userAddressService.findById(Integer.parseInt(id));
        return JSON.toJSONString(userAddress);
    }
    /**
     *修改收货地址
     */
    @ApiOperation("修改收货地址")
    @RequestMapping(value = "/modifyUserAddress",method = RequestMethod.GET)
    private String modifyUserAddress(String id, String consignee, String address, String phone, String email, String xaddress){
        UserAddress userAddress=new UserAddress(consignee,address,phone,email,xaddress);
        int uid=Integer.parseInt(id);
        userAddress.setId(uid);
        boolean flag=userAddressService.modifyUserAddress(userAddress);
        return "";
    }
    /**
     * 重置默认
     */
    @ApiOperation("重置默认收货地址")
    @RequestMapping(value = "/allisDefault",method = RequestMethod.GET)
    private String allisDefault(String userId){
        boolean flag=userAddressService.allisDefault(Integer.parseInt(userId));
        return "";
    }
    /**
     * 设置默认
     */
    @ApiOperation("设置默认收货地址")
    @RequestMapping(value = "/isDefault",method = RequestMethod.GET)
    private String isDefault(String id){
        boolean flag=userAddressService.isDefault(Integer.parseInt(id));
        return "";
    }
    /**
     * 查询默认地址
     */
    @ApiOperation("查询默认收货地址")
    @RequestMapping(value = "/findByUser",method = RequestMethod.GET)
    private  String findByUser(String userId){
        UserAddress userAddress=userAddressService.findByUser(Integer.parseInt(userId));
        return JSON.toJSONString(userAddress);
    }
}
