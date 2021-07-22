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
    /**
     * 查询最新的5条新闻数据 按照时间排序
     * @return
     */
    @RequestMapping("/getNewsDesc")
    @ResponseBody
    public String getNewsDesc(){
        List<News> list=newsService.getNewsDesc();
        return JSON.toJSONString(list);
    }
    /**
     * 查询所有的新闻数据
     */
    @RequestMapping("/getAllNews")
    @ResponseBody
    public String getNews(){
        List<News> list=newsService.getNews();
        return JSON.toJSONString(list);
    }
    /**
     * 添加新闻
     */
    @RequestMapping("/addNews")
    @ResponseBody
    public boolean addNews(){

        return true;
    }
    /**
     * 删除新闻
     */
    @RequestMapping("/removeNews")
    @ResponseBody
    public boolean removeNews(){

        return true;
    }
    /**
     * 修改新闻
     */
    @RequestMapping("/modifyNews")
    @ResponseBody
    public boolean modifyNews(){
        return true;
    }
}
