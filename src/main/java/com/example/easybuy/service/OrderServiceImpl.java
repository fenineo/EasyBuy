package com.example.easybuy.service;

import com.example.easybuy.entity.Order;
import com.example.easybuy.mapper.OrderMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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

    @Override
    public List<Order> findOrderPage(int pageIndex, int pageSize) {
        //计算分页查询开始位置
        Integer _pageIndex = (pageIndex-1)*pageSize;
        return orderMapper.findOrderPage(_pageIndex,pageSize);
    }

    @Override
    public int findOrderCount() {
        return orderMapper.findOrderCount();
    }

    @Override
    public List<Order> findOrderPageByUser(int pageIndex, int pageSize, int userId) {
        //计算分页查询开始位置
        Integer _pageIndex = (pageIndex-1)*pageSize;
        return orderMapper.findOrderPageByUser(_pageIndex,pageSize,userId);
    }

    @Override
    public int findOrderCountByUser(int userId) {
        return orderMapper.findOrderCountByUser(userId);
    }
}
