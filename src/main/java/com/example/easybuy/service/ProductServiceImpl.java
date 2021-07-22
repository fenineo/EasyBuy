package com.example.easybuy.service;

import com.example.easybuy.entity.Product;
import com.example.easybuy.mapper.ProductMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService{
    @Resource
    private ProductMapper productMapper;

    @Override
    public boolean addProduct(Product product) {
        boolean flag = false;
        if (productMapper.addProduct(product) > 0){
            flag = true;
        }
        return flag;
    }

    @Override
    public boolean removeProduct(int id) {
        boolean flag = false;
        if (productMapper.removeProduct(id) > 0){
            flag = true;
        }
        return flag;
    }

    @Override
    public boolean modifyProduct(Product product) {
        boolean flag = false;
        if (productMapper.modifyProduct(product) > 0){
            flag = true;
        }
        return flag;
    }

    @Override
    public List<Product> findProductList() {
        return productMapper.findProductList();
    }

    @Override
    public Product findById(int id) {
        return productMapper.findById(id);
    }
}
