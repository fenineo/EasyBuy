package com.example.easybuy.controller;
import com.alibaba.fastjson.JSON;
import com.example.easybuy.entity.Product;
import com.example.easybuy.entity.ProductCategory;
import com.example.easybuy.service.ProductService;
import com.example.easybuy.tools.PageBeanAll;
import com.example.easybuy.tools.SftpUtil;
import com.jcraft.jsch.SftpException;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/products")
public class Modle {
    @Autowired
    public ProductService productService;
    @RequestMapping("/getPageProduct")
    public String getPageProduct(String pageIndex,String isDelete){
        if(pageIndex==null || pageIndex.equals("")){
            pageIndex="1";
        }
        int _isDelete=Integer.parseInt(isDelete);
        int _pageIndex = Integer.parseInt(pageIndex);
        long totalCount = productService.findCountProduct(_isDelete);
        List<Product> pageList=productService.getPageProduct((_pageIndex-1)*10,10,_isDelete);
        PageBeanAll productPage = new PageBeanAll(_pageIndex,10,totalCount);
        productPage.setList(pageList);
        return JSON.toJSONString(productPage);
    }
    @RequestMapping("/addProduct")
        public String addProduct(@RequestParam("name") String name,
                                 @RequestParam("price") Integer price,
                                 @RequestParam("stock") Integer stock,
                                 @RequestParam("categoryLevel1Id") Integer categoryLevel1Id,
                                 @RequestParam("categoryLevel2Id") Integer categoryLevel2Id,
                                 @RequestParam("categoryLevel3Id") Integer categoryLevel3Id,
                                 @RequestParam("acth") MultipartFile acth,
                                 HttpServletRequest request) throws IOException, SftpException {
        Product product=new Product();
        product.setName(name);
        product.setPrice(price);
        product.setStock(stock);
        product.setCategoryLevel1Id(categoryLevel1Id);
        product.setCategoryLevel2Id(categoryLevel2Id);
        product.setCategoryLevel3Id(categoryLevel3Id);
        //取名字给图片
        String fileNames = System.currentTimeMillis() + RandomUtils.nextInt(0, 10000) + "_Personal.jpg";
//      product.setFileName(fileName);

        //连接
        SftpUtil sftp = new SftpUtil("fnztion", "123456", "192.168.137.192", 22);
        //登录
        sftp.login();
        //输出到服务器特定目录
        sftp.upload("/home/fnztion/apache-tomcat-8.5.69/webapps/img/zxc", fileNames, acth.getInputStream());
        //退出
        sftp.logout();
        product.setFileName(fileNames);
        boolean flag = productService.addProduct(product);
        return "true";
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
        System.out.println("成功");
        return JSON.toJSONString(product);
    }
    /**
     * 修改
     */
    @RequestMapping("/modifyProduct")
    public String modifyProduct(@RequestParam("name") String name,
                                @RequestParam("id") String id,
                                @RequestParam("price") Integer price,
                                @RequestParam("stock") Integer stock,
                                @RequestParam("categoryLevel1Id") Integer categoryLevel1Id,
                                @RequestParam("categoryLevel2Id") Integer categoryLevel2Id,
                                @RequestParam("categoryLevel3Id") Integer categoryLevel3Id,
                                @RequestParam("acth") MultipartFile acth,
                                HttpServletRequest request) throws IOException, SftpException {
        boolean empty=acth.isEmpty();
        if (empty) {
            Product product1=productService.findById(id);
            Product product=new Product();
            product.setName(name);
            product.setPrice(price);
            product.setStock(stock);
            product.setCategoryLevel1Id(categoryLevel1Id);
            product.setCategoryLevel2Id(categoryLevel2Id);
            product.setCategoryLevel3Id(categoryLevel3Id);
            product.setFileName(product1.getFileName());
            product.setId(id);
            boolean flag=productService.modifyProduct(product);
            return JSON.toJSONString(flag);
        }else {
            //取名字给图片
            String fileNames = System.currentTimeMillis() + RandomUtils.nextInt(0, 10000) + "_Personal.jpg";
            //连接
            SftpUtil sftp = new SftpUtil("fnztion", "123456", "192.168.137.192", 22);
            //登录
            sftp.login();
            //输出到服务器特定目录
            sftp.upload("/home/fnztion/apache-tomcat-8.5.69/webapps/img/zxc", fileNames, acth.getInputStream());
            //退出
            sftp.logout();
            Product product=new Product();
            product.setName(name);
            product.setPrice(price);
            product.setStock(stock);
            product.setCategoryLevel1Id(categoryLevel1Id);
            product.setCategoryLevel2Id(categoryLevel2Id);
            product.setCategoryLevel3Id(categoryLevel3Id);
            product.setFileName(fileNames);
            product.setId(id);
            boolean flag=productService.modifyProduct(product);
            return JSON.toJSONString(flag);
        }

    }
    /**
     * 商品的逻辑删除
     */
    @RequestMapping("/removeProduct")
    public String deleteProduct(String id){
        Product product=productService.findById(id);
        if (product.getIsDelete()==0){
            product.setIsDelete(1);
            boolean flag=productService.modifyProduct(product);
        }else if(product.getIsDelete()==1){
            product.setIsDelete(0);
            boolean flag=productService.modifyProduct(product);
        }
        return "";
    }
}
