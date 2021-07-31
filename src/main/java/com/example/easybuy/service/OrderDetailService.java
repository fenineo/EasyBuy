package com.example.easybuy.service;

import com.example.easybuy.entity.OrderDetail;

import java.util.List;

public interface OrderDetailService {
    boolean addOrderDetail(OrderDetail orderDetail);//添加订单
    boolean removeOrderDetail(int id);//删除订单
    boolean modifyOrderDetail(OrderDetail orderDetail);//修改订单
    List<OrderDetail> findByOrderId(int orderId);//根据订单号查询订单订单详细信息
}
