package com.example.easybuy.service;

import com.example.easybuy.entity.Order;
import com.example.easybuy.entity.OrderDetail;
import com.example.easybuy.entity.OrderDetailVo;

import java.util.List;

public interface OrderDetailService {
    boolean addOrderDetail(OrderDetail orderDetail);//添加订单
    boolean removeOrderDetail(int id);//删除订单
    boolean modifyOrderDetail(OrderDetail orderDetail);//修改订单
    List<OrderDetail> findByOrderId(int orderId);//根据订单号查询订单订单详细信息
    List<OrderDetailVo> findByOrderIdList(List<Order> list);//根据id集合查询订单详细商品信息
}
