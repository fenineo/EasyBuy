package com.example.easybuy.controller;

import com.alibaba.fastjson.JSON;
import com.example.easybuy.entity.Product;
import com.example.easybuy.entity.ProductCategory;
import com.example.easybuy.entity.User;
import com.example.easybuy.service.ProductCategoryService;
import com.example.easybuy.service.ProductService;
import com.example.easybuy.tools.PageBeanAll;
import com.example.easybuy.tools.SftpUtil;
import com.jcraft.jsch.SftpException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/product")
@Api(tags = "商品层Controller",description = "操作商品数据")
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductCategoryService productCategoryService;
    @Autowired
    private RedisTemplate redisTemplate;

    //获取商品分类信息和商品信息
    @ApiOperation("获取商品分类信息和商品信息")
    @RequestMapping(value = "/tourist/productList",method = RequestMethod.POST)
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
    @ApiOperation("根据商品id获取商品信息和分类路径")
    @RequestMapping(value = "/tourist/productInfo",method = RequestMethod.POST)
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
    @ApiOperation("根据商品分类查询商品集合")
    @RequestMapping(value = "/tourist/productInfoBycategory",method = RequestMethod.POST)
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
    @ApiOperation("根据商品名查询商品集合")
    @RequestMapping(value = "/tourist/productInfoByName",method = RequestMethod.POST)
    public HashMap<String,Object> productInfoByName(int pageIndex,int pageSize,String name,@RequestParam(required = false) String orderBy){

        long totalCount = productService.findCountByName(name);
        List<Product> productList = productService.findPageByName(pageIndex,pageSize,name,orderBy);
        PageBeanAll productPage = new PageBeanAll(pageIndex,pageSize,totalCount);
        productPage.setList(productList);

        HashMap<String,Object> map = new HashMap<>();
        map.put("productPage",productPage);
        return map;
    }

    //查询收藏夹信息
    @ApiOperation("查询收藏夹信息")
    @RequestMapping(value = "/findFavorite",method = RequestMethod.POST)
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
    @ApiOperation("用户收藏夹添加商品")
    @RequestMapping(value = "/addFavorite",method = RequestMethod.POST)
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
    @ApiOperation("用户收藏夹移除商品")
    @RequestMapping(value = "/removeFavorite",method = RequestMethod.POST)
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

    /**
     * 分页查询所有商品，后台展示
     */
    @ApiOperation("分页查询所有商品，后台展示")
    @RequestMapping(value = "/admin/getPageProduct",method =RequestMethod.GET)
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

    /**
     * 添加商品 包括文件上传
     */
    @ApiOperation("添加商品")
    @RequestMapping(value = "/admin/addProduct",method = RequestMethod.POST)
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
    @ApiOperation("分级查询")
    @RequestMapping(value = "/admin/getCategoryLevel",method = RequestMethod.GET)
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
//    /**
//     * 根据Id查询单个商品
//     */
//    @RequestMapping("/getPById")
//    public String getPById(String id){
//        Product product=productService.findById(id);
//        System.out.println("成功");
//        return JSON.toJSONString(product);
//    }
    /**
     * 修改
     */
    @ApiOperation("修改商品")
    @RequestMapping(value = "/admin/modifyProduct",method = RequestMethod.POST)
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
    @ApiOperation("商品的删除")
    @RequestMapping(value = "/admin/removeProduct",method = RequestMethod.GET)
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
