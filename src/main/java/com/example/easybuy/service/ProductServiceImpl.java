package com.example.easybuy.service;

import com.example.easybuy.entity.News;
import com.example.easybuy.entity.Product;
import com.example.easybuy.entity.ProductCategory;
import com.example.easybuy.mapper.ProductMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

//@Service
//public class ProductServiceImpl implements ProductService{
//    @Resource
//    private ProductMapper productMapper;
//
//    @Override
//    public boolean addProduct(Product product) {
//        boolean flag = false;
//        if (productMapper.addProduct(product) > 0){
//            flag = true;
//        }
//        return flag;
//    }
//
//    @Override
//    public boolean removeProduct(String id) {
//        boolean flag = false;
//        if (productMapper.removeProduct(id) > 0){
//            flag = true;
//        }
//        return flag;
//    }
//
//    @Override
//    public boolean modifyProduct(Product product) {
//        boolean flag = false;
//        if (productMapper.modifyProduct(product) > 0){
//            flag = true;
//        }
//        return flag;
//    }
//
//    @Override
//    public List<Product> findProductList() {
//        return productMapper.findProductList();
//    }
//
//    @Override
//    public Product findById(String id) {
//        return productMapper.findById(id);
//    }
//
//    @Override
//    public List<ProductCategory> findByType(int type) {
//        return productMapper.findByType(type);
//    }
//
//    @Override
//    public List<Product> findPageByCategory(int pageIndex, int pageSize, int categoryLevel3Id) {
//        //计算分页查询开始位置
//        Integer _pageIndex = (pageIndex-1)*pageSize;
//        return productMapper.findPageByCategory(_pageIndex,pageSize,categoryLevel3Id);
//    }
//
//    @Override
//    public long findCountByCategory(int categoryLevel3Id) {
//        return productMapper.findCountByCategory(categoryLevel3Id);
//    }
//
//    @Override
//    public List<Product> getPageProduct(int pageIndex, int pageSize) {
//        List<Product> list = productMapper.getPageProduct(pageIndex,pageSize);
//        return list;
//    }
//}
