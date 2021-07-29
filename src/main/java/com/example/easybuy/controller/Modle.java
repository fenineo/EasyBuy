package com.example.easybuy.controller;
import com.alibaba.fastjson.JSON;
import com.example.easybuy.entity.Product;
import com.example.easybuy.entity.ProductCategory;
import com.example.easybuy.service.ProductService;
import com.example.easybuy.tools.PageBean;
import com.example.easybuy.tools.PageBeanAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/products")
public class Modle {
    @Autowired
    public ProductService productService;
    @RequestMapping("/tourist/getPageProduct")
    public String getPageProduct(String pageIndex){
        if(pageIndex==null || pageIndex.equals("")){
            pageIndex="1";
        }
        int _pageIndex = Integer.parseInt(pageIndex);
        int totalCount =productService.findProductList().size();
        List<Product> pageList=productService.getPageProduct((_pageIndex-1)*10,10);
        PageBeanAll productPage = new PageBeanAll(_pageIndex,10,totalCount);
        productPage.setList(pageList);
        return JSON.toJSONString(productPage);
    }
    @RequestMapping("/tourist/addProduct")
    public String addProduct(String formData){
        Product product=new Product();
        product.setName("葫芦娃");
        product.setPrice(1234);
        product.setStock(1000);
        product.setCategoryLevel1Id(681);
        product.setCategoryLevel2Id(682);
        product.setCategoryLevel3Id(688);
        product.setFileName(formData);
        boolean flag=productService.addProduct(product);
        System.out.println(flag);
        return null;
    }
    /**
     * 分级查询
     */
    @RequestMapping("/tourist/getCategoryLevel")
    public HashMap<String,Object> getCategoryLevel(){
        List<ProductCategory> one=productService.findByType(1);
        List<ProductCategory> two=productService.findByType(2);
        List<ProductCategory> three=productService.findByType(3);
        HashMap<String,Object> map = new HashMap<>();
        map.put("one",one);
        System.out.println(one.toString());
        map.put("two",two);
        map.put("three",three);
        return map;
    }
}
