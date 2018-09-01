package com.demo.solrj.util;

import com.demo.solrj.domain.Malfunction;
import com.demo.solrj.exception.MyException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.params.MapSolrParams;
import org.apache.solr.common.params.SolrParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @Description: 连接 solr
 * @author: 01369674
 * @date: 2018/8/30
 */
public class SolrClientManager {

    private static final String SOLR_URL = "http://127.0.0.1:8983/solr/malfunction";
    private static final Integer CONNECTION_TIMEOUT = 10000;
    private static final Integer SOCKET_TIMEOUT = 60000;

    private HttpSolrClient httpSolrClient;

    private static Logger logger = LoggerFactory.getLogger(SolrClientManager.class);

    public void destory(){
        try {
            httpSolrClient.close();
        } catch (IOException e) {
            logger.debug("关闭 httpSolrClient 失败");
            throw new MyException("关闭 httpSolrClient 失败");
        }
    }

    public void init(){
        init(SOLR_URL,CONNECTION_TIMEOUT,SOCKET_TIMEOUT);
    }

    public void init(String url,Integer connectionTimeout,Integer socketTimeout){
        httpSolrClient = new HttpSolrClient.Builder(url)
                .withConnectionTimeout(connectionTimeout)
                .withSocketTimeout(socketTimeout)
                .build();
    }

    /**
     * 查询
     * @param queryParamMap
     * @return
     */
    public SolrDocumentList query(Map<String, String> queryParamMap){
        //[3]添加到SolrParams对象
        MapSolrParams queryParams = new MapSolrParams(queryParamMap);
        return query(queryParams);
    }

    public SolrDocumentList query(SolrParams queryParams){
        QueryResponse response = doQuery(queryParams);

        if(response==null){
            return null;
        }else{
            logger.debug("查询成功:打印查询参数和结果");
            return response.getResults();
        }
    }

    private QueryResponse doQuery(SolrParams queryParams){
        //[4]执行查询返回QueryResponse
        QueryResponse response = null;
        try {
            response = httpSolrClient.query(queryParams);
        } catch (Exception e) {
            logger.debug("查询失败:打印查询参数");
            throw new MyException("查询失败");
        }

        return response;
    }

    public List<Malfunction> queryBean(SolrParams queryParams){
        QueryResponse response = doQuery(queryParams);
        return response.getBeans(Malfunction.class);
    }
}
