package com.example.easybuy.service;

import com.example.easybuy.entity.News;
import com.example.easybuy.mapper.NewsMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class NewsServiceImpl implements NewsService{
    @Resource
    private NewsMapper newsMapper;
    @Override
    public List<News> getNewsDesc() {
        List<News> list = newsMapper.getNewsDesc();
        return list;
    }

    @Override
    public boolean addNews(News news) {
        boolean flag = false;
        if (newsMapper.addNews(news) > 0){
            flag = true;
        }
        return flag;
    }

    @Override
    public boolean removeNews(int id) {
        boolean flag = false;
        if (newsMapper.removeNews(id) > 0){
            flag = true;
        }
        return flag;
    }

    @Override
    public boolean modifyNews(News news) {
        boolean flag = false;
        if (newsMapper.modifyNews(news) > 0){
            flag = true;
        }
        return flag;
    }

    @Override
    public List<News> getNews() {
        List<News> list = newsMapper.getNews();
        return list;
    }
    public List<News> getPageNews(int pageIndex,int pageSize){
        List<News> list = newsMapper.getPageNews(pageIndex,pageSize);
        return list;
    }

    @Override
    public News findById(int id) {
        News news=newsMapper.findById(id);
        return news;
    }
}
