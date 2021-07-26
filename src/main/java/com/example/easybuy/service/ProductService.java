package com.example.easybuy.service;

import com.example.easybuy.entity.Product;
import com.example.easybuy.entity.ProductCategory;

import java.util.List;

public interface ProductService {
    boolean addProduct(Product product);    //添加商品
    boolean removeProduct(int id);          //删除商品
    boolean modifyProduct(Product product); //修改商品
    List<Product> findProductList();        //查询所有商品
    Product findById(int id);               //查询商品id
    List<ProductCategory> findByType(int type);    //根据分类等级查询分类信息
}
