package com.example.easybuy.controller;

import com.example.easybuy.entity.Product;
import com.example.easybuy.entity.ProductCategory;
import com.example.easybuy.service.ProductCategoryService;
import com.example.easybuy.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductCategoryService productCategoryService;
    //获取商品分类信息和商品信息
    @RequestMapping("/productList")
    public HashMap<String,Object> productList(){
        HashMap<String,Object> map = new HashMap<>();
        List<ProductCategory> categoryOneList = productService.findByType(1);
        List<ProductCategory> categoryTwoList = productService.findByType(2);
        List<ProductCategory> categoryThreeList = productService.findByType(3);
        List<Product> productList = productService.findProductList();
        map.put("categoryOneList",categoryOneList);
        map.put("categoryTwoList",categoryTwoList);
        map.put("categoryThreeList",categoryThreeList);
        map.put("productList",productList);
        return map;
    }

    @RequestMapping("/productInfo")
    public HashMap<String,Object> productInfo(String id){
        int _id = Integer.parseInt(id);
        System.out.println(_id);
        Product product = productService.findById(_id);
        ProductCategory categoryLv1 = productCategoryService.findById(product.getCategoryLevel1Id());
        ProductCategory categoryLv2 = productCategoryService.findById(product.getCategoryLevel2Id());
        ProductCategory categoryLv3  = productCategoryService.findById(product.getCategoryLevel3Id());
        String path = "全部 > ";

        path += categoryLv1.getName()+" > "+categoryLv2.getName()+" > "+categoryLv3.getName()+" > "+product.getName();
        HashMap<String,Object> map = new HashMap<>();
        map.put("path",path);
        map.put("product",product);
        return map;
    }

    @RequestMapping("/demo")
    public String demo(String id){
        return "";
    }

}
