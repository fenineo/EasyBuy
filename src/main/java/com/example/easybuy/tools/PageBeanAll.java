package com.example.easybuy.tools;

import java.util.List;

/**
 * 分页工具类
 */
public class PageBeanAll {
    private int pageIndex;      //页码
    private int pageSize;       //页容量
    private long pageCount;     //总页数
    private long totalCount;    //总记录数
    private List list;          //分页集合

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

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }

    public long getPageCount() {
        this.pageCount = totalCount%pageSize==0?totalCount/pageSize:(totalCount/pageSize)+1;
        return pageCount;
    }

    public PageBeanAll(int pageIndex, int pageSize, long totalCount) {
        super();
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
        this.totalCount = totalCount;
        this.pageCount = totalCount%pageSize==0?totalCount/pageSize:(totalCount/pageSize)+1;
    }

    public PageBeanAll() {
        super();
    }
}
