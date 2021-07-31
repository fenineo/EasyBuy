package com.example.easybuy.service;

import com.example.easybuy.entity.Order;

public interface OrderService {
    boolean addOrder(Order order);//添加订单
    boolean removeOrder(int id);//删除订单
    boolean modifyOrder(Order order);//修改订单
    Order findById(int id);//根据订单id查询订单信息
    Order findBySerialNumber(String serialNumber);//根据订单号查询订单信息
}
