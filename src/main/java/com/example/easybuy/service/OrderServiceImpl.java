package com.example.easybuy.service;

import com.example.easybuy.entity.Order;
import com.example.easybuy.mapper.OrderMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class OrderServiceImpl implements OrderService{
    @Resource
    private OrderMapper orderMapper;


    @Override
    public boolean addOrder(Order order) {
        boolean flag = false;
        if (orderMapper.addOrder(order) > 0){
            flag = true;
        }
        return flag;
    }

    @Override
    public boolean removeOrder(int id) {
        boolean flag = false;
        if (orderMapper.removeOrder(id) > 0){
            flag = true;
        }
        return flag;
    }

    @Override
    public boolean modifyOrder(Order order) {
        boolean flag = false;
        if (orderMapper.modifyOrder(order) > 0){
            flag = true;
        }
        return flag;
    }

    @Override
    public Order findById(int id) {
        return orderMapper.findById(id);
    }

    @Override
    public Order findBySerialNumber(String serialNumber) {
        return orderMapper.findBySerialNumber(serialNumber);
    }
}
