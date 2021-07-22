package com.example.easybuy.service;

import com.example.easybuy.entity.Order;

public interface OrderService {
    boolean addOrder(Order order);//添加订单
    boolean removeOrder(int id);//删除订单
    boolean modifyOrder(Order order);//修改订单
}
