package com.example.easybuy.controller;
import com.alibaba.fastjson.JSON;
import com.example.easybuy.entity.Product;
import com.example.easybuy.entity.ProductCategory;
import com.example.easybuy.service.ProductService;
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
    @RequestMapping("/getPageProduct")
    public String getPageProduct(String pageIndex){
        if(pageIndex==null || pageIndex.equals("")){
            pageIndex="1";
        }
        int _pageIndex = Integer.parseInt(pageIndex);
        long totalCount =productService.findProductCount();
        List<Product> pageList=productService.getPageProduct((_pageIndex-1)*10,10);
        PageBeanAll productPage = new PageBeanAll(_pageIndex,10,totalCount);
        productPage.setList(pageList);
        return JSON.toJSONString(productPage);
    }
    @RequestMapping("/addProduct")
    public String addProduct(String name,String price,String stock,String fileName,String categoryLevel1Id,String categoryLevel2Id,String categoryLevel3Id){
        Product product=new Product();
        product.setName(name);
        product.setPrice(Integer.parseInt(price));
        product.setStock(Integer.parseInt(stock));
        product.setCategoryLevel1Id(Integer.parseInt(categoryLevel1Id));
        product.setCategoryLevel2Id(Integer.parseInt(categoryLevel2Id));
        product.setCategoryLevel3Id(Integer.parseInt(categoryLevel3Id));
        product.setFileName(fileName);
        boolean flag= false;
        flag = productService.addProduct(product);
        System.out.println(flag);
        return "";
    }
    /**
     * 分级查询
     */
    @RequestMapping("/getCategoryLevel")
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
    /**
     * 根据Id查询
     */
    @RequestMapping("/getPById")
    public String getPById(String id){
        Product product=productService.findById(id);
        return JSON.toJSONString(product);
    }
    /**
     * 修改
     */
    @RequestMapping("/modifyProduct")
    public String modifyProduct(String id,String name,String price,String stock,String fileName,String categoryLevel1Id,String categoryLevel2Id,String categoryLevel3Id){
        Product product=new Product();
        product.setId(id);
        product.setName(name);
        product.setPrice(Integer.parseInt(price));
        product.setStock(Integer.parseInt(stock));
        product.setCategoryLevel1Id(Integer.parseInt(categoryLevel1Id));
        product.setCategoryLevel2Id(Integer.parseInt(categoryLevel2Id));
        product.setCategoryLevel3Id(Integer.parseInt(categoryLevel3Id));
        product.setFileName(fileName);
        boolean flag=productService.modifyProduct(product);
        System.out.println(flag);
        return null;
    }
}
