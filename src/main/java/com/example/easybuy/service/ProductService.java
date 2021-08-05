package com.example.easybuy.service;

import com.example.easybuy.entity.Product;
import com.example.easybuy.entity.ProductCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductService {
    boolean addProduct(Product product);        //添加商品，并返回添加时自动生成的id
    boolean removeProduct(String id);           //删除商品
    boolean modifyProduct(Product product);     //修改商品
    List<Product> findProductList();            //查询所有商品
    int findCountProduct(@Param("isDelete") int isDelete);                     //查询商品总记录数
    Product findById(String id);                //查询商品id
    List<ProductCategory> findByType(int type);     //根据分类等级查询分类信息
    List<Product> findPageByCategory(int pageIndex,int pageSize,int categoryLevel3Id);    //根据分类分页查询商品信息
    List<Product> getPageProduct(int pageIndex, int pageSize,int isDelete);      //分页查询

    int findCountByCategory(int categoryLevel3Id);      //根据三级分类查询总记录数
    List<Product> findPageByName(int pageIndex, int pageSize,String name,String orderBy);      //根据商品名分页查询集合
    long findCountByName(String name);       //根据商品名查询总记录数
}
