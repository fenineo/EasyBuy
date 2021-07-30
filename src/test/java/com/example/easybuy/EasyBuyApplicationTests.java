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

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

@SpringBootTest
class EasyBuyApplicationTests {
    @Autowired
    private SolrClient solrClient;//solr操作对象
    @Resource
    private ProductService productService;//我的商品类接口

    @Test
    void contextLoads() throws SolrServerException, IOException {
        //为solr库添加数据
        List<Product> productList = productService.findProductList();
        solrClient.addBeans(productList);
        solrClient.commit();
    }

    @Test
    void contextLoads1() throws SolrServerException, IOException {
        //删除所有数据
        solrClient.deleteByQuery("*:*");
        solrClient.commit();
    }

    @Test
    void contextLoads3() throws SolrServerException, IOException {
        //根据商品id查询数据
        SolrQuery query = new SolrQuery();
        query.setQuery("id:756");
        QueryResponse qr = null;
        try {
            qr = solrClient.query(query);
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Product product = qr.getBeans(Product.class).get(0);
        System.out.println(product.toString());
    }

}
