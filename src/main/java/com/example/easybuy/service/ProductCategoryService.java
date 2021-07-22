package com.example.easybuy.service;

import com.example.easybuy.entity.ProductCategory;

import java.util.List;

public interface ProductCategoryService {
    public List<ProductCategory> findProductCategoryList();
    boolean addProductCategory(ProductCategory productCategory);
    boolean removeProductCategory(int id);
    boolean modifyProductCategory(ProductCategory productCategory);
    ProductCategory findById(int id);
}
