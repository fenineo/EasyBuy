package com.example.easybuy.tools;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * MD5加密工具类
 */
public class MD5Util {
    /**
     * 一次MD5加密
     * @param value
     * @return
     */
    public static String md5Hex(String value){
        return DigestUtils.md5Hex(value);
    }

    /**
     * 三次MD5加密
     * @param value
     * @return
     */
    public static String md5Hex3(String value){
        for (int i = 0;i < 3;i++){
            value = DigestUtils.md5Hex(value);
        }
        return value;
    }
}
