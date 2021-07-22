package com.example.easybuy.mapper;

import com.example.easybuy.entity.Product;

import java.util.List;

public interface ProductMapper {
    int addProduct(Product product);
    int removeProduct(int id);
    int modifyProduct(Product product);
    List<Product> findProductList();
    Product findById(int id);
}
