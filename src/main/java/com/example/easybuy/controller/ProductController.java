package com.example.easybuy.controller;

import com.example.easybuy.entity.Product;
import com.example.easybuy.entity.ProductCategory;
import com.example.easybuy.service.ProductCategoryService;
import com.example.easybuy.service.ProductService;
import com.example.easybuy.tools.JwtTool;
import com.example.easybuy.tools.PageBeanAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductCategoryService productCategoryService;
    @Autowired
    private RedisTemplate redisTemplate;

    //获取商品分类信息和商品信息
    @RequestMapping("/tourist/productList")
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
    //根据商品id获取商品信息和分类路径
    @RequestMapping("/tourist/productInfo")
    public HashMap<String,Object> productInfo(int id){
        Product product = productService.findById(id);
        ProductCategory categoryLv1 = productCategoryService.findById(product.getCategoryLevel1Id());
        ProductCategory categoryLv2 = productCategoryService.findById(product.getCategoryLevel2Id());
        ProductCategory categoryLv3  = productCategoryService.findById(product.getCategoryLevel3Id());
        String path = "全部 > ";
        if(categoryLv1 != null){
            path += categoryLv1.getName()+" > ";
        }
        if(categoryLv2 != null){
            path += categoryLv2.getName()+" > ";
        }
        if(categoryLv3 != null){
            path += categoryLv3.getName()+" > ";
        }
        path += product.getName();
        HashMap<String,Object> map = new HashMap<>();
        map.put("path",path);
        map.put("product",product);
        return map;
    }
    //根据商品分类查询商品集合
    @RequestMapping("/tourist/productInfoBycategory")
    public HashMap<String,Object> productInfoBycategory(int pageIndex,int pageSize,int categoryId){
        ProductCategory categoryLv3  = productCategoryService.findById(categoryId);
        ProductCategory categoryLv2 = productCategoryService.findById(categoryLv3.getParentId());
        ProductCategory categoryLv1 = productCategoryService.findById(categoryLv2.getParentId());
        String path = "全部 > ";
        if(categoryLv1 != null){
            path += categoryLv1.getName()+" > ";
        }
        if(categoryLv2 != null){
            path += categoryLv2.getName()+" > ";
        }
        if(categoryLv3 != null){
            path += categoryLv3.getName();
        }

        int totalCount = productService.findCountByCategory(categoryId);
        List<Product> productList = productService.findPageByCategory(pageIndex,pageSize,categoryId);
        PageBeanAll productPage = new PageBeanAll(pageIndex,pageSize,totalCount);
        productPage.setList(productList);

        HashMap<String,Object> map = new HashMap<>();
        map.put("path",path);
        map.put("productPage",productPage);
        return map;
    }

    //为购物车添加商品
    @RequestMapping("/addShopping")
    public HashMap<String,Object> addShopping(String token,int productId,int number){
        Product product = productService.findById(productId);       //根据商品id查询商品
        //根据token信息生成key
        HashMap<String,Object> user = JwtTool.parseMap(token);
        String key = user.get("id")+""+user.get("loginName");
        //返回给前端的数据
        HashMap<String,Object> map = new HashMap<>();
        map.put("flag",false);

        List<Product> shoppingProduct = new ArrayList<>();

        if(redisTemplate.hasKey(key)){
            //从redis获取购物车
            shoppingProduct = redisTemplate.opsForList().range(key,0,-1);
            for (int i = 0;i < shoppingProduct.size();i++){
                if (shoppingProduct.get(i).getId() == productId){
                    shoppingProduct.get(i).setStock(shoppingProduct.get(i).getStock()+number);
                    redisTemplate.opsForList().set(key,i,shoppingProduct.get(i));
                    map.put("shoppingProduct",shoppingProduct);
                    map.put("flag",true);
                    return map;
                }
            }
        }

        product.setStock(number);
        if (redisTemplate.opsForList().rightPushAll(key,product) > 0){
            shoppingProduct = redisTemplate.opsForList().range(key,0,-1);
            map.put("shoppingProduct",shoppingProduct);
            map.put("flag",true);
        }

        return map;
    }
    //修改购物车中商品的数量
    @RequestMapping("/modifyShopping")
    public HashMap<String,Object> modifyShopping(String token,int productId,int number){
        //根据token信息生成key
        HashMap<String,Object> user = JwtTool.parseMap(token);
        String key = user.get("id")+""+user.get("loginName");

        HashMap<String,Object> map = new HashMap<>();
        map.put("flag",false);

        //从redis获取购物车
        List<Product> shoppingProduct = redisTemplate.opsForList().range(key,0,-1);

        if (shoppingProduct != null){
            for (int i = 0;i < shoppingProduct.size();i++){
                if (shoppingProduct.get(i).getId() == productId){
                    shoppingProduct.get(i).setStock(number);
                    redisTemplate.opsForList().set(key,i,shoppingProduct.get(i));
                    map.put("shoppingProduct",shoppingProduct);
                    map.put("flag",true);
                }
            }
        }

        return map;
    }
    //删除购物车中的商品
    @RequestMapping("/removeShopping")
    public HashMap<String,Object> removeShopping(String token,int productId){
        //根据token信息生成key
        HashMap<String,Object> user = JwtTool.parseMap(token);
        String key = user.get("id")+""+user.get("loginName");

        HashMap<String,Object> map = new HashMap<>();
        map.put("flag",false);

        //从redis获取购物车
        List<Product> shoppingProduct = redisTemplate.opsForList().range(key,0,-1);

        if (shoppingProduct != null){
            for (int i = 0;i < shoppingProduct.size();i++){
                if (shoppingProduct.get(i).getId() == productId){
                    redisTemplate.opsForList().remove(key,1,shoppingProduct.get(i));
                    shoppingProduct = redisTemplate.opsForList().range(key,0,-1);
                    map.put("flag",true);
                    break;
                }
            }
        }
        map.put("shoppingProduct",shoppingProduct);
        return map;
    }
    //根据token查询购物车信息
    @RequestMapping("/findShopping")
    public List<Product> findShopping(String token){
        //根据token信息生成key
        HashMap<String,Object> user = JwtTool.parseMap(token);
        String key = user.get("id")+""+user.get("loginName");

        List<Product> shoppingProduct = new ArrayList<>();

        if(redisTemplate.hasKey(key)){
            //从redis获取购物车
            shoppingProduct = redisTemplate.opsForList().range(key,0,-1);
        }

        return shoppingProduct;
    }

//    @RequestMapping("/demo")
//    public String demo(String id){
//        return "";
//    }

}
