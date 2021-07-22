package com.example.easybuy.service;

import com.example.easybuy.entity.Product;

import java.util.List;

public interface ProductService {
    boolean addProduct(Product product);
    boolean removeProduct(int id);
    boolean modifyProduct(Product product);
    List<Product> findProductList();
    Product findById(int id);
}
