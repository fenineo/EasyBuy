package com.example.easybuy.service;

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

@Service
public class ProductServiceSolrImpl implements ProductService{
    @Resource
    private ProductMapper productMapper;
    @Autowired
    private SolrClient solrClient;
    private SolrQuery query = new SolrQuery();
    @Override
    public boolean addProduct(Product product) {
        boolean flag = false;
        if (productMapper.addProduct(product) > 0){
            try {
                solrClient.addBean(product);
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
        query.setQuery("*:*");
        QueryResponse qr = null;
        try {
            qr = solrClient.query(query);
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<Product> productList = qr.getBeans(Product.class);
        return productList;
    }

    @Override
    public Product findById(String id) {
        query.setQuery("id:"+id);
        QueryResponse qr = null;
        try {
            qr = solrClient.query(query);
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Product product = qr.getBeans(Product.class).get(0);
        return product;
    }

    @Override
    public List<ProductCategory> findByType(int type) {
        return productMapper.findByType(type);
    }

    @Override
    public List<Product> findPageByCategory(int pageIndex, int pageSize, int categoryLevel3Id) {
        //计算分页查询开始位置
        Integer _pageIndex = (pageIndex-1)*pageSize;
        query.setQuery("icategoryLevel3Id_i:"+categoryLevel3Id);
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
        return productList;
    }

    @Override
    public long findCountByCategory(int categoryLevel3Id) {
        query.setQuery("icategoryLevel3Id_i:"+categoryLevel3Id);
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

    @Override
    public List<Product> getPageProduct(int pageIndex, int pageSize) {
        query.setStart(pageIndex);
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
        return productList;
    }
}
