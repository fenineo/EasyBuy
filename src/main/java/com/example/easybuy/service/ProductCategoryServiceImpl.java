package com.example.easybuy.service;

import com.example.easybuy.entity.ProductCategory;
import com.example.easybuy.mapper.ProductCategoryMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService{
    @Resource
    public ProductCategoryMapper productCategoryMapper;

    @Override
    public List<ProductCategory> findProductCategoryList() {
        return productCategoryMapper.findProductCategoryList();
    }

    @Override
    public boolean addProductCategory(ProductCategory productCategory) {
        boolean flag = false;
        if (productCategoryMapper.addProductCategory(productCategory)> 0){
            flag = true;
        }
        return flag;
    }

    @Override
    public boolean removeProductCategory(int id) {
        boolean flag = false;
        if (productCategoryMapper.removeProductCategory(id)> 0){
            flag = true;
        }
        return flag;
    }

    @Override
    public boolean modifyProductCategory(ProductCategory productCategory) {
        boolean flag = false;
        if (productCategoryMapper.modifyProductCategory(productCategory)> 0){
            flag = true;
        }
        return flag;
    }

    @Override
    public ProductCategory findById(int id) {
        return productCategoryMapper.findById(id);
    }

    @Override
    public List<ProductCategory> onecategoryLevel1Id() {
        return productCategoryMapper.onecategoryLevel1Id();
    }

    @Override
    public List<ProductCategory> onecategoryLevel2Id() {
        return productCategoryMapper.onecategoryLevel2Id();
    }

    @Override
    public List<ProductCategory> onecategoryLevel3Id() {
        return productCategoryMapper.onecategoryLevel3Id();
    }
}
