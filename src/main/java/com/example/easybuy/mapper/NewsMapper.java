package com.example.easybuy.mapper;

import com.example.easybuy.entity.News;

import java.util.List;

public interface NewsMapper {
    List<News> getNewsDesc();//查询5条的方法 在主页面
    int addNews(News news);//添加新闻
    int removeNews(int id);//删除新闻
    int modifyNews(News news);//修改新闻



}
