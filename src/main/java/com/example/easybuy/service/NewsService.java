package com.example.easybuy.service;

import com.example.easybuy.entity.News;

import java.util.List;

public interface NewsService {
    List<News> getNewsDesc();//查询5条的方法 在主页面
    boolean addNews(News news);//添加新闻
    boolean removeNews(int id);//删除新闻
    boolean modifyNews(News news);//修改新闻

}
