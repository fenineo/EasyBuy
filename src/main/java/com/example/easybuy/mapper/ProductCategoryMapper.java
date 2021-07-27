package com.example.easybuy.mapper;
/**
 * 产品类别表实体类
 */

import com.example.easybuy.entity.ProductCategory;
import com.example.easybuy.entity.User;
import org.apache.ibatis.annotations.Param;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductCategoryMapper {
    List<ProductCategory> findProductCategoryList();            //查询所有商品分类
    int addProductCategory(ProductCategory productCategory);    //添加商品分类
    int removeProductCategory(int id);                          //删除商品分类
    int modifyProductCategory(ProductCategory productCategory); //修改商品分类
    ProductCategory findById(int id);                           //查询id
    List<ProductCategory> onecategoryLevel1Id();                //查询一级分类商品
    List<ProductCategory> onecategoryLevel2Id();                //查询所有商品分类
    List<ProductCategory> onecategoryLevel3Id();                //商品type从小到大ASC查询
    int findcategoryCount();                                    //查询商品分类数量
    List<ProductCategory> findcategoryPage(@Param("pageIndex") int pageIndex, @Param("pageSize")int pageSize);       //商品分类数据分页查询LIMIT
}
