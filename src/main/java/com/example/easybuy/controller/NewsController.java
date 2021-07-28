package com.example.easybuy.controller;

import com.alibaba.fastjson.JSON;
import com.example.easybuy.entity.News;
import com.example.easybuy.service.NewsService;
import com.example.easybuy.tools.PageBean;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/News")
public class NewsController {
    @Resource
    private NewsService newsService;
    /**
     * 查询最新的5条新闻数据 按照时间排序
     * @return
     */
    @RequestMapping("/getNewsDesc")
    public String getNewsDesc(){
        List<News> list=newsService.getNewsDesc();
        return JSON.toJSONString(list);
    }
    /**
     * 查询所有的新闻数据
     */
    @RequestMapping("/getAllNews")
    public String getNews(){
        List<News> list=newsService.getNews();
        return JSON.toJSONString(list);
    }
    /**
     * 添加新闻
     */
    @RequestMapping("/addNews")
    public boolean addNews(String title,String content){
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());
        String time=formatter.format(date);;
        News news=new News();
        news.setTitle(title);
        news.setContent(content);
        news.setCreateTime(time);
        boolean flag=newsService.addNews(news);
        return flag;
    }
    /**
     * 删除新闻
     */
    @RequestMapping("/removeNews")
    public String removeNews(String id){
        String a;
        boolean flag=newsService.removeNews(Integer.parseInt(id));
        if(flag){
            a="true";
        }else {
            a="false";
        }
        return a;
    }
    /**
     * 修改新闻
     */
    @RequestMapping("/modifyNews")
    public boolean modifyNews(String id,String title,String content){
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());
        String time=formatter.format(date);;
        News news=new News(Integer.parseInt(id),title,content,time);
        boolean flag=newsService.modifyNews(news);
        return flag;
    }
    /**
     *分页查询
     */
    @RequestMapping("/getPageNews")
    public String getPageNews(String pageIndex){
//        if(currentpage==null || currentpage.equals("")){
//            currentpage= "1";
//        }
//        List<News> list=newsService.getNews();
//        List<News> pageList=newsService.getPageNews((Integer.parseInt(currentpage)-1)*10, 10);
//        PageBean page=new PageBean();
//        page.setList(pageList);//查询出来的集合
//        page.setTotalCount(list.size());
//        page.setPageSize(10);//每一页查询的条数
//        page.setCurrentpage(Integer.parseInt(currentpage));//当前是第几页
        if(pageIndex==null || pageIndex.equals("")){
            pageIndex="1";
        }
        int _pageIndex = Integer.parseInt(pageIndex);
        int totalCount =newsService.getNews().size();
        List<News> pageList=newsService.getPageNews((Integer.parseInt(pageIndex)-1)*10, 10);
        PageBean pageBean=new PageBean(_pageIndex,10,totalCount);
        pageBean.setList(pageList);
        return JSON.toJSONString(pageBean);
    }

    /**
     *根据Id查询 查询新闻详情
     */
    @RequestMapping("/findById")
    public String findById(String id){
        News news=newsService.findById(Integer.parseInt(id));
        return JSON.toJSONString(news);
    }
}
