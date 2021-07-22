package com.example.easybuy.service;

import com.example.easybuy.entity.OrderDetail;

public interface OrderDetailService {
    boolean addOrderDetail(OrderDetail orderDetail);//添加订单
    boolean removeOrderDetail(int id);//删除订单
    boolean modifyOrderDetail(OrderDetail orderDetail);//修改订单
}
