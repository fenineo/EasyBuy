package com.example.easybuy.service;

import com.example.easybuy.entity.Order;
import com.example.easybuy.entity.OrderDetail;
import com.example.easybuy.entity.OrderDetailVo;
import com.example.easybuy.mapper.OrderDetailMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class OrderDetailServiceImpl implements OrderDetailService{
    @Resource
    private OrderDetailMapper orderDetailMapper;
    @Override
    public boolean addOrderDetail(OrderDetail orderDetail) {
        boolean flag = false;
        if (orderDetailMapper.addOrderDetail(orderDetail) > 0){
            flag = true;
        }
        return flag;
    }

    @Override
    public boolean removeOrderDetail(int id) {
        boolean flag = false;
        if (orderDetailMapper.removeOrderDetail(id) > 0){
            flag = true;
        }
        return flag;
    }

    @Override
    public boolean modifyOrderDetail(OrderDetail orderDetail) {
        boolean flag = false;
        if (orderDetailMapper.modifyOrderDetail(orderDetail) > 0){
            flag = true;
        }
        return flag;
    }

    @Override
    public List<OrderDetail> findByOrderId(int orderId) {
        return orderDetailMapper.findByOrderId(orderId);
    }

    @Override
    public List<OrderDetailVo> findByOrderIdList(List<Order> list) {
        return orderDetailMapper.findByOrderIdList(list);
    }
}
