package com.example.easybuy.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.easybuy.entity.ProductCategory;
import com.example.easybuy.service.ProductCategoryService;
import com.example.easybuy.service.ProductService;
import com.example.easybuy.tools.PageBeanAll;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/productCategory")
@Api(tags = "分类列表层Controller",description = "操作管理员模块分类列表数据")
public class ProductCategorController {
    @Autowired
    public ProductService productService;

    @Autowired
    public ProductCategoryService productCategoryService;

    /**
     * 查询一级列表
     * @param productCategory
     * @return
     */
    @ApiOperation("查询一级列表")
    @RequestMapping(value = "/admin/onecategoryLevel1",method = RequestMethod.POST)
    public String onecategoryLevel1(ProductCategory productCategory){
        List<ProductCategory> list1 = productCategoryService.onecategoryLevel1();
        return JSON.toJSONString(list1);
    }

    /**
     * 查询二级列表
     * @param productCategory
     * @return
     */
    @ApiOperation("查询二级列表")
    @RequestMapping(value = "/admin/onecategoryLevel2",method = RequestMethod.POST)
    public String onecategoryLevel2(ProductCategory productCategory){
        List<ProductCategory> list2 =productCategoryService.onecategoryLevel2();
        return JSON.toJSONString(list2);
    }

    /**
     * 查询三级列表
     * @param productCategory
     * @return
     */
    @ApiOperation("查询三级列表")
    @RequestMapping(value = "/admin/onecategoryLevel3",method = RequestMethod.POST)
    public String onecategoryLevel3(ProductCategory productCategory){
        List<ProductCategory> list3 =productCategoryService.onecategoryLevel2();
        return JSON.toJSONString(list3);
    }

    /**
     * 查询左侧导航一二三级
     * 专题
     * @param productCategory
     * @return
     */
    @ApiOperation("查询左侧导航一二三级")
    @RequestMapping(value = "/tourist/twocategoryLevel2Id",method = RequestMethod.POST)
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
     * 查询所有数据
     * @param productCategory
     * @return
     */
    @ApiOperation("查询所有数据")
    @RequestMapping(value = "/admin/findProductCategory",method = RequestMethod.POST)
    public String findProductCategory(ProductCategory productCategory){
        List<ProductCategory> list1 =productCategoryService.findProductCategoryList();
        return JSON.toJSONString(list1);
    }
    /**
     * 添加商品分类
     * @return
     */
    @ApiOperation("添加商品分类")
    @RequestMapping(value = "/admin/addProductCategory",method = RequestMethod.POST)
    public boolean addproductCategor(String name,String parentId,String type){
        ProductCategory productCategory = new ProductCategory();
        productCategory.setName(name);
        productCategory.setParentId(Integer.parseInt(parentId));
        productCategory.setType(Integer.parseInt(type));
        boolean flag=productCategoryService.addProductCategory(productCategory);
        if(flag==true){
            return flag;
        }else{
            flag=false;
            return flag;
        }
    }

    /**
     * 删除商品分类
     * @param id
     * @return
     */
    @ApiOperation("删除商品分类")
    @RequestMapping(value = "/admin/removeProductCategory",method = RequestMethod.POST)
    public boolean removeproductCategory(String id){
        ProductCategory productCategory = new ProductCategory();
        productCategory.setId(Integer.parseInt(id));
        boolean flag = productCategoryService.removeProductCategory(Integer.parseInt(id));
        if (flag){
            return true;
        }else {
            return false;
        }
    }

    /**
     * 修改商品分类
     * @param id
     * @param name
     * @return
     */
    @ApiOperation("修改商品分类")
    @RequestMapping(value = "/admin/modifyProductCategory",method = RequestMethod.POST)
    public String modifyproductCategory(String id,String name,String parentId,String type){
        ProductCategory productCategory = new ProductCategory();
        productCategory.setId(Integer.parseInt(id));
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
    @ApiOperation("查询商品分类id")
    @RequestMapping(value = "/admin/findById",method = RequestMethod.POST)
    public String findById(String id){
        ProductCategory productCategory = productCategoryService.findById(Integer.parseInt(id));
        return JSON.toJSONString(productCategory);
    }

    /**
     * 分类列表
     * @param pageIndex
     * @return
     */
    @ApiOperation("分类列表")
    @RequestMapping(value = "/admin/categorylist",method = RequestMethod.POST)
    public HashMap<String,Object> categorlsit1(String pageIndex){
        int _pageIndex = Integer.parseInt(pageIndex);
        int totalCount = productCategoryService.findcategoryCount();
        List<ProductCategory> productCategoryList2 = productCategoryService.findProductCategoryList();
        List<ProductCategory> productCategoryList = productCategoryService.findcategoryPage(_pageIndex,10);
        PageBeanAll categorypage = new PageBeanAll(_pageIndex,10,totalCount);
        categorypage.setList(productCategoryList);

        HashMap<String,Object> map = new HashMap<>();
        map.put("categorypage",categorypage);
        map.put("productCategoryList2",productCategoryList2);
        return map;
    }

    /**
     * 分级查询
     */
    @ApiOperation("分级查询")
    @RequestMapping(value = "/admin/getCategoryLevel",method = RequestMethod.POST)
    public HashMap<String,Object> getCategoryLevel(){
        List<ProductCategory> one=productService.findByType(1);
        List<ProductCategory> two=productService.findByType(2);
        List<ProductCategory> three=productService.findByType(3);
        HashMap<String,Object> map = new HashMap<>();
        map.put("one",one);
        map.put("two",two);
        map.put("three",three);
        return map;
    }
}
