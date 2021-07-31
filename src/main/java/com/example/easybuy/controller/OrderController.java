package com.example.easybuy.controller;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.example.easybuy.config.AlipayConfig;
import com.example.easybuy.entity.Order;
import com.example.easybuy.entity.OrderDetail;
import com.example.easybuy.entity.Product;
import com.example.easybuy.service.OrderDetailService;
import com.example.easybuy.service.OrderService;
import com.example.easybuy.service.ProductService;
import com.example.easybuy.tools.JwtTool;
import com.example.easybuy.tools.OrderNumberUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderDetailService orderDetailService;
    @Autowired
    private ProductService productService;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private AlipayConfig alipayConfig;

    //添加订单请求，传入token，地址，总金额。添加订单成功后返回map数组，包含 flag:成功信息,orderNumber:订单号
    @RequestMapping("/addOrder")
    public HashMap<String,Object> addOrder(HttpServletRequest request,String address,float sum) throws ParseException {
        String token = request.getHeader("token");
        HashMap<String,Object> user = JwtTool.parseMap(token);//用token获取用户信息
        int id = Integer.parseInt(user.get("id")+"");
        String loginName = user.get("loginName")+"";

        boolean flag = false;
        Date date = new Date();//获取当前时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");//自定义时间格式
        String orderNumber = OrderNumberUtil.getOrderNumber();

        //创建订单对象
        System.out.println(sum);
        Order order = new Order(0,id,loginName,address, sdf.parse(sdf.format(date)),sum,0, orderNumber);
        if (orderService.addOrder(order)){
            order = orderService.findBySerialNumber(orderNumber);//根据订单号查询订单

            //根据用户id和登陆名信息生成key
            String key = user.get("id")+""+user.get("loginName");
            List<Product> shoppingProduct = new ArrayList<>();
            if(redisTemplate.hasKey(key)){
                //从redis获取购物车
                shoppingProduct = redisTemplate.opsForList().range(key,0,-1);
                //循环插入订单详细信息
                for (int i = 0;i < shoppingProduct.size();i++){
                    int productId = Integer.parseInt(shoppingProduct.get(i).getId());      //商品主键
                    int quantity = shoppingProduct.get(i).getStock();                      //数量
                    double cost = quantity*shoppingProduct.get(i).getPrice();              //消费
                    OrderDetail orderDetail = new OrderDetail(0,order.getId(),productId,quantity,cost);
                    orderDetailService.addOrderDetail(orderDetail);
                }
                //清空购物车
                redisTemplate.delete(key);
            }
            flag = true;
        }

        HashMap<String,Object> resultMap = new HashMap<>();
        resultMap.put("flag",flag);
        resultMap.put("orderId",order.getId());
        return resultMap;
    }

//    @RequestMapping("/demo")
//    public String demo(String id){
//        return "";
//    }

    @RequestMapping("/findOrderInfo")
    public Order findOrderInfo(int orderId){
        return orderService.findById(orderId);
    }

    //跳转到支付宝支付页面
    @RequestMapping("/alipay")
    public String alipay(int orderId){
        Order order = orderService.findById(orderId);
        return alipayConfig.toPayPage(order.getLoginName(),order.getSerialNumber(),order.getCost()+"");
    }

    //同步
    @RequestMapping("/return")
    public String returnUrl(HttpServletRequest request) throws UnsupportedEncodingException, AlipayApiException {
        return "";
    }

    //异步
    @RequestMapping("/tourist/notify")
    public String notifyUrl(HttpServletRequest request) throws UnsupportedEncodingException, AlipayApiException {
        System.out.println("异步效验");
        Map<String,String> params = new HashMap<String,String>();
        Map<String,String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用
//            valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }

        boolean signVerified = AlipaySignature.rsaCheckV1(params,alipayConfig.getPublicKey(),"utf-8",alipayConfig.getSignType()); //调用SDK验证签名
        //——请在这里编写您的程序（以下代码仅作参考）——
        Map<String,String> dmap = new HashMap<>();
        if(signVerified) {
            //商户订单号
            String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");
            //根据商户订单号获取订单
            Order order = orderService.findBySerialNumber(out_trade_no);
            //付款金额
            String total_amount = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"),"UTF-8");

            //通过订单号查询订单详细集合
            List<OrderDetail> orderDetailList = orderDetailService.findByOrderId(order.getId());
            for(int i = 0;i < orderDetailList.size();i++){
                //根据详细订单中的商品id查找商品对象
                Product product = productService.findById(orderDetailList.get(i).getProductId()+"");
                //修改商品的库存
                product.setStock(product.getStock()-orderDetailList.get(i).getQuantity());
                productService.modifyProduct(product);
            }
            //修改订单支付状态
            order.setStatePay(1);
            orderService.modifyOrder(order);
        }else {
            System.out.println("验签失败");
        }
        return JSON.toJSONString(dmap);
    }

}
