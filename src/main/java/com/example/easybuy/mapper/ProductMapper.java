package com.example.easybuy.mapper;

import com.example.easybuy.entity.Product;
import com.example.easybuy.entity.ProductCategory;

import java.util.List;

public interface ProductMapper {
    int addProduct(Product product);    //添加商品
    int removeProduct(int id);          //删除商品
    int modifyProduct(Product product); //修改商品
    List<Product> findProductList();    //查询所有商品
    Product findById(int id);           //查询商品id
    List<ProductCategory> findByType(int type);    //根据分类等级查询分类信息
}
