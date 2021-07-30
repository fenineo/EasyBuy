package com.example.easybuy.mapper;

import com.example.easybuy.entity.Product;
import com.example.easybuy.entity.ProductCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductMapper {
    int addProduct(Product product);        //添加商品
    int removeProduct(String id);           //删除商品
    int modifyProduct(Product product);     //修改商品
    List<Product> findProductList();        //查询所有商品
    int findCountProduct();                 //查询商品总记录数
    Product findById(String id);            //查询商品id
    List<ProductCategory> findByType(int type);         //根据分类等级查询分类信息
    List<Product> findPageByCategory(@Param("pageIndex") int pageIndex,@Param("pageSize") int pageSize,@Param("categoryLevel3Id") int categoryLevel3Id);    //根据分类分页查询商品信息
    int findCountByCategory(int categoryLevel3Id);      //查询总记录数
    List<Product> getPageProduct(@Param("pageIndex") int pageIndex, @Param("pageSize") int pageSize);   //分页查询商品
}
