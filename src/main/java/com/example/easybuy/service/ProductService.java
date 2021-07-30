package com.example.easybuy.service;

import com.example.easybuy.entity.News;
import com.example.easybuy.entity.Product;
import com.example.easybuy.entity.ProductCategory;
import org.apache.ibatis.annotations.Param;
import org.apache.solr.client.solrj.SolrServerException;

import java.io.IOException;
import java.util.List;

public interface ProductService {
    boolean addProduct(Product product);    //添加商品
    boolean removeProduct(String id);          //删除商品
    boolean modifyProduct(Product product); //修改商品
    List<Product> findProductList();        //查询所有商品
    Product findById(String id);               //查询商品id
    List<ProductCategory> findByType(int type);    //根据分类等级查询分类信息
    List<Product> findPageByCategory(int pageIndex,int pageSize,int categoryLevel3Id);    //根据分类分页查询商品信息
    long findCountByCategory(int categoryLevel3Id);      //查询总记录数
    List<Product> getPageProduct(int pageIndex, int pageSize);//分页查询
}
