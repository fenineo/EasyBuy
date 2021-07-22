package com.example.easybuy.mapper;
/**
 * 产品类别表实体类
 */

import com.example.easybuy.entity.ProductCategory;

import java.util.List;

public interface ProductCategoryMapper {
    List<ProductCategory> findProductCategoryList();
    int addProductCategory(ProductCategory productCategory);
    int removeProductCategory(int id);
    int modifyProductCategory(ProductCategory productCategory);
    ProductCategory findById(int id);

}
