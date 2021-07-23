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

        List<ProductCategory> list2 =productCategoryService.onecategoryLevel2Id();
        return JSON.toJSONString(list2);
    }

    @RequestMapping("/twocategoryLevel2Id")
    public String onecategoryLevel2Id(ProductCategory productCategory){
        List<ProductCategory> list1 =productCategoryService.onecategoryLevel3Id();

        int i,u,y;
        for (i=0;i<list1.size()-1;i++){
            if(list1.get(i).getType()==1) {
                System.out.println("===========1============"+list1.get(i).getId());
            }
            for (u=0;u<list1.size();u++){
                if (list1.get(u).getType()==2 && list1.get(u).getParentId()==list1.get(i).getId()){
                    System.out.println("=========2========"+list1.get(u).getParentId());
                }
                for (u=0;u<list1.size();u++){
                    if (list1.get(u).getType()==3 && list1.get(u).getParentId()==list1.get(i).getId()){
                        System.out.println("======3======"+list1.get(u).getParentId());
                    }
                }
            }
        }
        return JSON.toJSONString(list1);
    }
}
