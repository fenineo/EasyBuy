package com.example.easybuy.tools;

import com.example.easybuy.entity.News;
import org.springframework.stereotype.Component;

import java.util.List;

public class PageBean {
    private int pageIndex;  //页码
    private int pageSize;   //页容量
    private int pageCount;  //总页数
    private int totalCount; //总记录数
    private int currentpage;//当前页
    private List<News> list;//分页集合

    public int getCurrentpage() {
        return currentpage;
    }

    public void setCurrentpage(int currentpage) {
        this.currentpage = currentpage;
    }

    public List<News> getList() {
        return list;
    }

    public void setList(List<News> list) {
        this.list = list;
    }

    public int getPageIndex() {
        return pageIndex;
    }
    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }
    public int getPageSize() {
        return pageSize;
    }
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
    public int getPageCount() {
        this.pageCount = totalCount%pageSize==0?totalCount/pageSize:(totalCount/pageSize)+1;
        return pageCount;
    }
    public int getTotalCount() {
        return totalCount;
    }
    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
    public PageBean(int pageIndex, int pageSize, int totalCount) {
        super();
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
        this.totalCount = totalCount;
    }
    public PageBean() {
        super();
    }
}
