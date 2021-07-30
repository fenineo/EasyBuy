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
    void contextLoads1() throws SolrServerException, IOException {
        solrClient.deleteByQuery("*:*");
        solrClient.commit();
    }

    @Test
    void contextLoads2() throws SolrServerException, IOException {
        SolrQuery query = new SolrQuery();
        query.setQuery("*:*");
        query.setFacetLimit(-1);
        QueryResponse qr = null;
        try {
            qr = solrClient.query(query);
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<Product> productList = qr.getBeans(Product.class);
        System.out.println(productList.size());
    }

    @Test
    void contextLoads3() throws SolrServerException, IOException {
        Product product = productService.findById("733");
        System.out.println(product.toString());
    }

}
