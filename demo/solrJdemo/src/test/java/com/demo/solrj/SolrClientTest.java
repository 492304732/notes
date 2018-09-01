package com.demo.solrj;

import com.demo.solrj.domain.Malfunction;
import com.demo.solrj.util.SolrClientManager;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: TODO
 * @author: 01369674
 * @date: 2018/8/30
 */
public class SolrClientTest {

    /**
     * 查询
     * @throws Exception
     */
    @Test
    public void querySolr() throws Exception{
        SolrClientManager solrClientManager = new SolrClientManager();
        solrClientManager.init();

        //[2]封装查询参数
        Map<String, String> queryParamMap = new HashMap<String, String>();
        queryParamMap.put("q", "*:*");

        SolrDocumentList documents = solrClientManager.query(queryParamMap);

        //[6]内容遍历
        for(SolrDocument doc : documents) {
            printDocument(doc);
        }

        solrClientManager.destory();
    }

     /**
      * 使用 SolrParams 的子类 SolrQuery,它提供了一些方便的方法
      */
    @Test
    public void querySolr2() throws Exception{
        SolrClientManager solrClientManager = new SolrClientManager();
        solrClientManager.init();

        SolrQuery query = generateQuery();

        SolrDocumentList documents = solrClientManager.query(query);

        //[6]内容遍历
        for(SolrDocument doc : documents) {
            printDocument(doc);
        }

        solrClientManager.destory();
    }

     /**
     * 7、Java对象绑定，通过对象查询索引
     */
    @Test
    public void queryBean() throws Exception{

        SolrClientManager solrClientManager = new SolrClientManager();
        solrClientManager.init();

        SolrQuery query = generateQuery();

        //[4]获取doc文档
        List<Malfunction> malfunctions = solrClientManager.queryBean(query);
        //[5]遍历
        for (Malfunction malfunction : malfunctions) {
            System.out.println(malfunction.toString());
        }
        //[6]关闭资源
        solrClientManager.destory();
    }

    private SolrQuery generateQuery(){

        //[2]创建SolrQuery对象
        SolrQuery query = new SolrQuery("*:*");
        //添加回显的内容
        query.addField("id");
        query.addField("title");
        query.addField("description");
        query.addField("module");
        query.addField("type");
        query.addField("solution");

        query.setRows(200);//设置每页显示多少条

        return query;
    }

    private void printDocument(SolrDocument doc){
        System.out.println("id:"+doc.get("id")
                +"\ttitle:"+doc.get("title")
                +"\tdescription:"+doc.get("description")
                +"\tmodule:"+doc.get("module")
                +"\ttype:"+doc.get("type")
                +"\tsolution:"+doc.get("solution"));
    }
}
