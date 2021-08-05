package com.example.easybuy.service;

import com.example.easybuy.entity.ProductCategory;

import java.util.List;

public interface ProductCategoryService {
    public List<ProductCategory> findProductCategoryList();             //查询所有商品分类
    boolean addProductCategory(ProductCategory productCategory);        //添加商品分类
    boolean removeProductCategory(int id);                              //删除商品分类
    boolean modifyProductCategory(ProductCategory productCategory);     //修改商品分类
    ProductCategory findById(int id);                                   //查询id
    List<ProductCategory> onecategoryLevel1Id();                        //查询所有商品分类
    List<ProductCategory> onecategoryLevel3Id();                        //商品type从小到大ASC查询
    int findcategoryCount();                                            //查询商品分类数量
    List<ProductCategory> findcategoryPage(int pageIndex, int pageSize);       //商品分类数据分页查询LIMIT
    List<ProductCategory> onecategoryLevel1();                  //查询一级分类商品
    List<ProductCategory> onecategoryLevel2();                  //查询二级分类商品
    List<ProductCategory> onecategoryLevel3();                  //查询三级分类商品
    ProductCategory findByCategoryName(String name);            //查分类名称是否存在
    ProductCategory findbyCategoryParentId(String name,int parentId,int type);//查询分类名称,父类Id,分类级别
}
