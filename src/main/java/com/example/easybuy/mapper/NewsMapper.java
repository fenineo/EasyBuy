package com.example.easybuy.mapper;

import com.example.easybuy.entity.News;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface NewsMapper {
    List<News> getNewsDesc();//查询5条的方法 在主页面
    int addNews(News news);//添加新闻
    int removeNews(int id);//删除新闻
    int modifyNews(News news);//修改新闻
    List<News> getNews();//查询所有的新闻
    List<News> getPageNews(@Param("currentpage")int currentpage,@Param("pageSize") int pageSize);//分页查询
    News findById(int id);




}
