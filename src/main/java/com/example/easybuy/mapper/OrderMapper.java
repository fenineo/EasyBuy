package com.example.easybuy.mapper;


import com.example.easybuy.entity.Order;

public interface OrderMapper {
    int addOrder(Order order);//添加订单
    int removeOrder(int id);//删除订单
    int modifyOrder(Order order);//修改订单
}
