package com.example.easybuy.controller;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.example.easybuy.config.AlipayConfig;
import com.example.easybuy.entity.Order;
import com.example.easybuy.service.OrderService;
import com.example.easybuy.tools.JwtTool;
import com.example.easybuy.tools.OrderNumberUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private AlipayConfig alipayConfig;

    //添加订单请求，传入token，地址，总金额。添加订单成功后返回map数组，包含flag:成功信息,orderNumber:订单号
    @RequestMapping("/addOrder")
    public HashMap<String,Object> addOrder(String token,String address,double sum){
        HashMap<String,Object> map = JwtTool.parseMap(token);//用token获取用户信息
        int id = Integer.parseInt(map.get("id")+"");
        String loginName = map.get("loginName")+"";
        boolean flag = false;
        Date date = new Date();//获取当前时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");//自定义时间格式
        String orderNumber = OrderNumberUtil.getOrderNumber();
        //创建订单对象
        Order order = new Order(0,id,loginName,address, sdf.format(date),sum,0, orderNumber);
        if (orderService.addOrder(order)){
            flag = true;
        }
        HashMap<String,Object> resultMap = new HashMap<>();
        resultMap.put("flag",flag);
        resultMap.put("orderNumber",orderNumber);
        return resultMap;
    }

//    @RequestMapping("/demo")
//    public String demo(String id){
//        return "";
//    }

    //跳转到支付宝支付页面
    @RequestMapping("/alipay")
    public String alipay(String subject,String orderNo,String amount){
        return alipayConfig.toPayPage(subject,orderNo,amount);
    }

    //同步
    @RequestMapping("/return")
    public String returnUrl(HttpServletRequest request) throws UnsupportedEncodingException, AlipayApiException {
        System.out.println("同步效验");//返回一个页面
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
            dmap.put("商户订单号：",out_trade_no);
            //支付宝交易号
            String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");
            dmap.put("支付宝交易号：",trade_no);
            //付款金额
            String total_amount = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"),"UTF-8");
            dmap.put("付款金额",total_amount);
            System.out.println("trade_no:"+trade_no+"<br/>out_trade_no:"+out_trade_no+"<br/>total_amount:"+total_amount);
        }else {
            System.out.println("验签失败");
        }
        return JSON.toJSONString(dmap);
    }

    //异步
    @RequestMapping("/notify")
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
            dmap.put("商户订单号：",out_trade_no);
            //支付宝交易号
            String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");
            dmap.put("支付宝交易号：",trade_no);
            //付款金额
            String total_amount = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"),"UTF-8");
            dmap.put("付款金额",total_amount);
            System.out.println("trade_no:"+trade_no+"<br/>out_trade_no:"+out_trade_no+"<br/>total_amount:"+total_amount);
        }else {
            System.out.println("验签失败");
        }
        return JSON.toJSONString(dmap);
    }

}
