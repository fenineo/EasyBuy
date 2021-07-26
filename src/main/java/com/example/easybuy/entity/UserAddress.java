package com.example.easybuy.entity;

import java.util.Date;

/**
 * 用户地址表实体类
 */
public class UserAddress {
    private int id;             //主键
    private int userId;         //用户主键
    private String consignee;   //收件人
    private String address;     //配送地址
    private String phone;       //电话号码
    private String email;       //邮箱
    private String xaddress;    //详细地址
    private Date createTime;    //创建时间
    private int isDefault;      //是否是默认地址（1:是 0否）
    private String remark;      //备注

    public UserAddress(int id, int userId, String address, String xaddress,Date createTime, int isDefault, String remark) {
        this.id = id;
        this.userId = userId;
        this.address = address;
        this.xaddress= xaddress;
        this.createTime = createTime;
        this.isDefault = isDefault;
        this.remark = remark;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserAddress() {
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }
    public String getXaddress() {
        return xaddress;
    }

    public void setXaddress(String xaddress) {
        this.xaddress = xaddress;
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

    @Override
    public String toString() {
        return "UserAddress{" +
                "id=" + id +
                ", userId=" + userId +
                ", consignee='" + consignee + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", xaddress='" + xaddress + '\'' +
                ", createTime=" + createTime +
                ", isDefault=" + isDefault +
                ", remark='" + remark + '\'' +
                '}';
    }
}
