package com.example.easybuy.controller;

import com.example.easybuy.entity.Product;
import com.example.easybuy.entity.ProductCategory;
import com.example.easybuy.entity.User;
import com.example.easybuy.service.ProductCategoryService;
import com.example.easybuy.service.ProductService;
import com.example.easybuy.tools.PageBeanAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
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
    public HashMap<String,Object> productInfo(String id){
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

        long totalCount = productService.findCountByCategory(categoryId);
        List<Product> productList = productService.findPageByCategory(pageIndex,pageSize,categoryId);
        PageBeanAll productPage = new PageBeanAll(pageIndex,pageSize,totalCount);
        productPage.setList(productList);

        HashMap<String,Object> map = new HashMap<>();
        map.put("path",path);
        map.put("productPage",productPage);
        return map;
    }
    //根据商品名查询商品集合
    @RequestMapping("/tourist/productInfoByName")
    public HashMap<String,Object> productInfoByName(int pageIndex,int pageSize,String name){

        long totalCount = productService.findCountByName(name);
        List<Product> productList = productService.findPageByName(pageIndex,pageSize,name);
        PageBeanAll productPage = new PageBeanAll(pageIndex,pageSize,totalCount);
        productPage.setList(productList);

        HashMap<String,Object> map = new HashMap<>();
        map.put("productPage",productPage);
        return map;
    }

    //查询收藏夹信息
    @RequestMapping("/findFavorite")
    public List<Product> findFavorite(HttpServletRequest request){
        String token = request.getHeader("token");
        String key = token+"favo"; //用token+shop作为购物车的key

        List<Product> favoriteList = new ArrayList<>();

        if(redisTemplate.hasKey(key)){
            //从redis获取购物车
            favoriteList = redisTemplate.opsForList().range(key,0,-1);
        }

        return favoriteList;
    }
    //用户收藏夹添加商品
    @RequestMapping("/addFavorite")
    public HashMap<String,Object> addFavorite(HttpServletRequest request, String productId){
        String token = request.getHeader("token");
        /**返回参数  flag:添加结果提示，favoriteList:收藏夹商品集合*/
        HashMap<String,Object> map = new HashMap<>();               //返回给前端的数据集合
        List<Product> favoriteList = new ArrayList<>();             //收藏夹集合
        boolean flag = false;                                       //默认添加失败

        String key = token+"favo"; //用token+favo作为收藏夹的key
        Product product = productService.findById(productId);       //根据商品id查询商品

        //判断用户是否已有收藏夹
        if(redisTemplate.hasKey(key)){
            favoriteList = redisTemplate.opsForList().range(key,0,-1);//查询收藏夹
            //判断商品是否重复
            for(int i = 0;i < favoriteList.size();i++){
                if (favoriteList.get(i).getId().equals(productId)){
                    map.put("flag",flag);
                    return map;
                }
            }
            //判断收藏夹是否超量
            if (favoriteList.size() > 7){
                redisTemplate.opsForList().set(key,0,product);   //修改redis中的数据;
                favoriteList.set(0,product);                        //收藏的商品已经有8件，替换第一件
                map.put("flag",true);
                map.put("favoriteList",favoriteList);
                return map;
            }
        }

        //收藏夹不存在，或者收藏夹未满且添加的商品不重复，向收藏夹新添商品
        if (redisTemplate.opsForList().rightPushAll(key,product) > 0){
            flag = true;
        }

        favoriteList = redisTemplate.opsForList().range(key,0,-1);//查询修改后的收藏夹商品集合
        map.put("flag",flag);
        map.put("favoriteList",favoriteList);
        return map;
    }
    //用户收藏夹移除商品
    @RequestMapping("/removeFavorite")
    public HashMap<String,Object> removeFavorite(HttpServletRequest request, String productId,int amount){
        String token = request.getHeader("token");
        /**返回参数  flag:结果提示，favoriteList:收藏夹商品集合*/
        HashMap<String,Object> map = new HashMap<>();               //返回给前端的数据集合
        List<Product> favoriteList = new ArrayList<>();             //收藏夹集合
        boolean flag = false;                                       //默认删除失败

        String key = token+"favo"; //用token+favo作为收藏夹的key

        //判断用户是否已有收藏夹
        if(redisTemplate.hasKey(key)){
            //amount==1，只删除一条数据
            if (amount == 1){
                Product product = productService.findById(productId);             //根据商品id查询商品
                favoriteList = redisTemplate.opsForList().range(key,0,-1);  //查询收藏夹
                //循环查找目标商品
                for(int i = 0;i < favoriteList.size();i++){
                    if (favoriteList.get(i).getId().equals(productId)){
                        redisTemplate.opsForList().remove(key,1,favoriteList.get(i));//先删除购物车内商品
                        favoriteList.remove(i);//同步删除商品集合内商品
                        flag = true;
                        break;
                    }
                }
            }else {
                //删除所有数据
                redisTemplate.delete(key);
                flag = true;
            }
        }

        map.put("flag",flag);
        map.put("favoriteList",favoriteList);
        return map;
    }

}
