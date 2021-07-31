package com.example.easybuy.entity;

import java.util.Date;

/**
 * 订单表实体类
 */
public class Order {
    private int id;                //主键
    private int userId;            //用户主键
    private String loginName;      //登陆名
    private String userAddress;    //用户地址
    private String createTime;       //创建时间
    private double cost;           //总消费
    private int statePay;          //订单支付状态(1：已支付，0：未支付)
    private String serialNumber;   //订单号

    public Order(int id, int userId, String loginName, String userAddress, String createTime, double cost, int statePay, String serialNumber) {
        this.id = id;
        this.userId = userId;
        this.loginName = loginName;
        this.userAddress = userAddress;
        this.createTime = createTime;
        this.cost = cost;
        this.statePay = statePay;
        this.serialNumber = serialNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public int getStatePay() {
        return statePay;
    }

    public void setStatePay(int statePay) {
        this.statePay = statePay;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }
}
