package com.example.easybuy.tools;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.easybuy.entity.User;

import java.util.HashMap;
import java.util.Map;

/**
 * Jwt工具类
 */
public class JwtTool {
    //对用户对象进行加密
    public static String budilJwt(User user){
        Map<String,Object> map = new HashMap<>();
        map.put("alg","HS256");
        map.put("typ","JWT");
        String token = JWT.create()
                .withHeader(map)
                .withClaim("id",user.getId())
                .withClaim("loginName",user.getLoginName())
                .withClaim("type",user.getType())
                .sign(Algorithm.HMAC256("mwi&$nf%"));
        return  token;
    }
    //判断token内是否存在用户信息
    public static boolean parseJwt(String token){
        //用盐获得可以解析token的JWTVerifier对象
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256("mwi&$nf%")).build();
        try {
            //解析token，获得解密文档
            DecodedJWT verify = verifier.verify(token);
        }catch (Exception e){
            return false;
        }
        return true;
    }
    //解析token，获得用户的登陆名和权限等级
    public static HashMap<String,Object> parseMap(String token){
        //用盐获得可以解析token的JWTVerifier对象
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256("mwi&$nf%")).build();
        //解析token，获得解密文档
        DecodedJWT verify = verifier.verify(token);
        //用解密文档获得载荷内容
        int id = verify.getClaim("id").asInt();
        String loginName = verify.getClaim("loginName").asString();
        int type = verify.getClaim("type").asInt();

        HashMap<String,Object> map = new HashMap<>();
        map.put("id",id);
        map.put("loginName",loginName);
        map.put("type",type);

        return map;
    }

}
