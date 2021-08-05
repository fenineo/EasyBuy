package com.example.easybuy.service;

import com.example.easybuy.entity.News;
import com.example.easybuy.entity.Product;
import com.example.easybuy.entity.ProductCategory;
import com.example.easybuy.mapper.ProductMapper;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public class ProductServiceImpl implements ProductService{
    @Resource
    private ProductMapper productMapper;
    @Autowired
    private SolrClient solrClient;

    @Override
    public boolean addProduct(Product product) {
        boolean flag = false;
        int id = productMapper.addProduct(product);
        if (id  > 0){
            try {
                product.setId(id+"");
                solrClient.addBean(product);
                solrClient.commit();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SolrServerException e) {
                e.printStackTrace();
            }
            flag = true;
        }
        return flag;
    }

    @Override
    public boolean removeProduct(String id) {
        boolean flag = false;
        if (productMapper.removeProduct(id) > 0){
            try {
                solrClient.deleteById(id);
                solrClient.commit();
            } catch (SolrServerException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            flag = true;
        }
        return flag;
    }

    @Override
    public boolean modifyProduct(Product product) {
        boolean flag = false;
        if (productMapper.modifyProduct(product) > 0){
            try {
                solrClient.addBean(product);
                solrClient.commit();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SolrServerException e) {
                e.printStackTrace();
            }
            flag = true;
        }
        return flag;
    }

    @Override
    public List<Product> findProductList() {
        return productMapper.findProductList();
    }

    @Override
    public int findCountProduct(int isDelete) {
        return productMapper.findCountProduct(isDelete);
    }

    @Override
    public Product findById(String id) {
        return productMapper.findById(id);
    }

    @Override
    public List<ProductCategory> findByType(int type) {
        return productMapper.findByType(type);
    }

    @Override
    public List<Product> findPageByCategory(int pageIndex, int pageSize, int categoryLevel3Id) {
        //计算分页查询开始位置
        Integer _pageIndex = (pageIndex-1)*pageSize;
        return productMapper.findPageByCategory(_pageIndex,pageSize,categoryLevel3Id);
    }

    @Override
    public int findCountByCategory(int categoryLevel3Id) {
        return productMapper.findCountByCategory(categoryLevel3Id);
    }

    @Override
    public List<Product> getPageProduct(int pageIndex, int pageSize,int isDelete) {
        return productMapper.getPageProduct(pageIndex,pageSize,isDelete);
    }

    public List<Product> findPageByName(int pageIndex, int pageSize,String name,String orderBy){
        //计算分页查询开始位置
        Integer _pageIndex = (pageIndex-1)*pageSize;

        SolrQuery query = new SolrQuery();
        query.setQuery("name_ik:"+name);
        query.addFilterQuery("isDelete_i:0");
        if (orderBy.equals("1")){
            query.setSort("price_d",SolrQuery.ORDER.desc);
        }else if (orderBy.equals("0")){
            query.setSort("price_d",SolrQuery.ORDER.asc);
        }

        query.setHighlight(true);
        query.addHighlightField("name_ik");
        query.setHighlightSimplePre("<font color=\"yellow\">");
        query.setHighlightSimplePost("</font>");

        query.setStart(_pageIndex);
        query.setRows(pageSize);
        QueryResponse qr = null;
        try {
            qr = solrClient.query(query);
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<Product> productList = qr.getBeans(Product.class);
        Map<String, Map<String,List<String>>> highlighting = qr.getHighlighting();
        for (int i = 0;i < productList.size();i++){
            productList.get(i).setName(highlighting.get(productList.get(i).getId()).get("name_ik").get(0));
        }
        return productList;
    }

    public long findCountByName(String name){
        SolrQuery query = new SolrQuery();
        query.setQuery("name_ik:"+name);
        query.addFilterQuery("isDelete_i:0");
        QueryResponse qr = null;
        try {
            qr = solrClient.query(query);
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        long count = qr.getResults().getNumFound();
        return count;
    }
}
