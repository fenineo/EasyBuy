package com.example.easybuy.service;

import com.example.easybuy.entity.Order;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderService {
    boolean addOrder(Order order);//添加订单
    boolean removeOrder(int id);//删除订单
    boolean modifyOrder(Order order);//修改订单
    Order findById(int id);//根据订单id查询订单信息
    Order findBySerialNumber(String serialNumber);//根据订单号查询订单信息
    List<Order> findOrderPage( int pageIndex, int pageSize);//分页查询订单信息
    int findOrderCount();//查询订单表总记录数
    List<Order> findOrderPageByUser(int pageIndex,int pageSize,int userId);//根据用户分页查询订单信息
    int findOrderCountByUser(int userId);//查询用户在订单表的订单记录数
}
