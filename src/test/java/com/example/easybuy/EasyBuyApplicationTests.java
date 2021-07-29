package com.example.easybuy;

import com.example.easybuy.entity.Product;
import com.example.easybuy.service.ProductService;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

@SpringBootTest
class EasyBuyApplicationTests {
    @Autowired
    private SolrClient solrClient;
    @Resource
    private ProductService productService;

    @Test
    void contextLoads() throws SolrServerException, IOException {
        List<Product> productList = productService.findProductList();
        solrClient.addBeans(productList);
        solrClient.commit();
    }

    @Test
    void contextLoads2() throws SolrServerException, IOException {
        SolrQuery query = new SolrQuery();
        query.setQuery("name_ik:香水");
        QueryResponse qr = solrClient.query(query);
        List<Product> list = qr.getBeans(Product.class);
        System.out.println(list.toString());
    }

}
