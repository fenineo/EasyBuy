package com.example.easybuy.tools;

import java.util.Calendar;

/**
 * 订单号生成工具类
 */
public class OrderNumberUtil {

    public static String getOrderNumber(){
        String orderNumber = "";//返回的订单号

        //拼接三个随机数
        for(int i = 0;i < 3;i++){
            orderNumber += (int)(Math.random()*10);
        }
        Calendar calendar = Calendar.getInstance();
//        orderNumber += calendar.get(Calendar.MONTH);//拼接月份
//        orderNumber += calendar.get(Calendar.DATE);//拼接日期
        orderNumber += calendar.get(Calendar.YEAR);//拼接年份
        //拼接三个随机数
        for(int i = 0;i < 3;i++){
            orderNumber += (int)(Math.random()*10);
        }
//        orderNumber += calendar.get(Calendar.HOUR);//拼接小时
        orderNumber += calendar.get(Calendar.MINUTE);//拼接分钟
        orderNumber += calendar.get(Calendar.SECOND);//拼接秒钟

        return orderNumber;
    }
}
