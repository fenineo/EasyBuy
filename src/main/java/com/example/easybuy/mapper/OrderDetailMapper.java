package com.example.easybuy.mapper;


import com.example.easybuy.entity.OrderDetail;

public interface OrderDetailMapper {
    int addOrderDetail(OrderDetail orderDetail);//添加订单
    int removeOrderDetail(int id);//删除订单
    int modifyOrderDetail(OrderDetail orderDetail);//修改订单
}
