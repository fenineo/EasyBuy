package com.example.easybuy.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.easybuy.entity.ProductCategory;
import com.example.easybuy.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/productCategory")
public class ProductCategorController {

    @Autowired
    public ProductCategoryService productCategoryService;

    /**
     * 查询一级列表
     * @param productCategory
     * @return
     */
    @RequestMapping("/onecategoryLevel1Id")
    public String onecategoryLevel1Id(ProductCategory productCategory){

        List<ProductCategory> list2 =productCategoryService.onecategoryLevel2Id();
        return JSON.toJSONString(list2);
    }

    /**
     * 查询左侧导航一二三级
     * 专题
     * @param productCategory
     * @return
     */
    @RequestMapping("/twocategoryLevel2Id")
    public String twocategoryLevel2Id(ProductCategory productCategory){
        List<ProductCategory> list1 =productCategoryService.onecategoryLevel3Id();

        int i,u,y;
        for (i=0;i<list1.size();i++){
            if(list1.get(i).getType()==1) {
                for (u=0;u<list1.size();u++){
                    if (list1.get(u).getType()==2 && list1.get(u).getParentId()==list1.get(i).getId()){
                        for (y=0;y<list1.size();y++){
                            if (list1.get(y).getType()==3 && list1.get(y).getParentId()==list1.get(u).getId()){
                            }
                        }
                    }
                }
            }
        }
        return JSON.toJSONString(list1);
    }

    /**
     * 添加商品分类
     * @return
     */
    @RequestMapping("/addProductCategory")
    public String addproductCategor(String name,String parentId,String type){
        ProductCategory productCategory = new ProductCategory();
        productCategory.setName(name);
        productCategory.setParentId(Integer.parseInt(parentId));
        productCategory.setType(Integer.parseInt(type));
        boolean flag=productCategoryService.addProductCategory(productCategory);
        if(flag==true){
            return "true";
        }else{
            return "false";
        }
    }

    /**
     * 删除商品分类
     * @return
     */
    @RequestMapping("/removeProductCategory")
    public String removeproductCategory(String id){
        ProductCategory productCategory = new ProductCategory();
        productCategory.setId(Integer.parseInt(id));
        boolean flag = productCategoryService.removeProductCategory(Integer.parseInt(id));
        if (flag){
            return "true";
        }else {
            return "false";
        }
    }

    /**
     * 修改商品分类
     * @return
     */
    @RequestMapping("/modifyProductCategory")
    public String modifyproductCategory(String name,String parentId,String type){
        ProductCategory productCategory = new ProductCategory();
        productCategory.setName(name);
        productCategory.setParentId(Integer.parseInt(parentId));
        productCategory.setType(Integer.parseInt(type));
        boolean flag = productCategoryService.modifyProductCategory(productCategory);
        if(flag){
            return "true";
        }else {
            return "false";
        }
    }

    /**
     * 查询商品分类id
     * @param id
     * @return
     */
    @RequestMapping("/findById")
    public String findById(String id){
        ProductCategory productCategory = productCategoryService.findById(Integer.parseInt(id));
        return JSON.toJSONString(productCategory);
    }
}
