package com.example.easybuy;

import com.example.easybuy.entity.Order;
import com.example.easybuy.entity.OrderDetail;
import com.example.easybuy.entity.OrderDetailVo;
import com.example.easybuy.entity.Product;
import com.example.easybuy.service.OrderDetailService;
import com.example.easybuy.service.OrderService;
import com.example.easybuy.service.ProductService;
import com.example.easybuy.tools.MD5Util;
import com.example.easybuy.tools.PageBeanAll;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@SpringBootTest
class EasyBuyApplicationTests {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private SolrClient solrClient;

    @Test
    void contextLoads() throws SolrServerException, IOException {
    }

    @Test
    void contextLoads1() throws SolrServerException, IOException {
    }

    @Test
    void contextLoads3() throws SolrServerException, IOException {
    }

    @Test
    void contextLoads4() throws SolrServerException, IOException {
    }

}
