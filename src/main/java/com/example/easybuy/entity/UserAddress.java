package com.example.easybuy.entity;

import java.util.Date;

/**
 * 用户地址表实体类
 */
public class UserAddress {
    private int id;             //主键
    private int userId;         //用户主键
    private String address;     //地址
    private Date createTime;    //创建时间
    private int isDefault;      //是否是默认地址（1:是 0否）
    private String remark;      //备注

    public UserAddress(int id, int userId, String address, Date createTime, int isDefault, String remark) {
        this.id = id;
        this.userId = userId;
        this.address = address;
        this.createTime = createTime;
        this.isDefault = isDefault;
        this.remark = remark;
    }

    public UserAddress() {
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(int isDefault) {
        this.isDefault = isDefault;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
