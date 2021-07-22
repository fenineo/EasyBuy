package com.example.easybuy.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.easybuy.entity.ProductCategory;
import com.example.easybuy.service.ProductCategoryService;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class ProductCategorController {


    @Resource
    public ProductCategoryService productCategoryService;

    @RequestMapping("/onecategoryLevel1Id")
    @ResponseBody
    public String onecategoryLevel1Id(ProductCategory productCategory){
        List<ProductCategory> list1 =productCategoryService.onecategoryLevel1Id();
        return JSON.toJSONString(list1);
    }

    @RequestMapping("/twocategoryLevel2Id")
    public String onecategoryLevel2Id(ProductCategory productCategory){
        List<ProductCategory> list2 = productCategoryService.onecategoryLevel2Id();
        return JSON.toJSONString(list2);
    }
}
