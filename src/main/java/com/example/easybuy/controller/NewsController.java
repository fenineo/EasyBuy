package com.example.easybuy.controller;

import com.alibaba.fastjson.JSON;
import com.example.easybuy.entity.News;
import com.example.easybuy.service.NewsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
public class NewsController {
    @Resource
    private NewsService newsService;
    @RequestMapping("/getNewsDesc")
    @ResponseBody
    public String getNewsDesc(){
        List<News> list=newsService.getNewsDesc();
        return JSON.toJSONString(list);
    }
}
