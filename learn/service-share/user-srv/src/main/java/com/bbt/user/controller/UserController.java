package com.bbt.user.controller;

import com.alibaba.fastjson.JSONObject;
import com.bbt.framework.web.BaseController;
import com.bbt.framework.web.Result;
import com.bbt.user.dto.UserDTO;
import com.bbt.user.service.UserService;
import com.bbt.user.util.PwdUtil;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * @Description: 描述
 * @Author:  zhangrc
 * @CreateDate:  2018/11/9 17:06
 */
@RestController
@RequestMapping(value = "user/")
public class UserController extends BaseController{

    @Autowired
    private UserService userService;

    @Autowired
    private TransportClient client;


    @RequestMapping(value = "register", method = RequestMethod.GET)
    public Result register(@RequestParam String account,@RequestParam String pwd) {
        userService.register(account,pwd);
        return successCreated();
    }


    @RequestMapping(value = "getUserById", method = RequestMethod.GET)
    public Result<String> getUserById(@RequestParam Integer id) {
        GetResponse result= this.client.prepareGet("user","users",id.toString()).get();
        ResponseEntity entity = new ResponseEntity(result.getSource(), HttpStatus.OK);
        String jsonStr= JSONObject.toJSONString(entity.getBody());
        JSONObject json = JSONObject.parseObject(jsonStr);
        return successGet(json.getString("account"));
    }


    @RequestMapping(value = "deleteUserById", method = RequestMethod.GET)
    public Result<String> deleteUserById(@RequestParam Integer id) {
        DeleteResponse result= this.client.prepareDelete("user","users",id.toString()).get();
        ResponseEntity entity = new ResponseEntity(result.getResult(), HttpStatus.OK);
        String jsonStr= JSONObject.toJSONString(entity.getBody());
        JSONObject json = JSONObject.parseObject(jsonStr);
        return successGet();
    }


    @RequestMapping(value = "saveUserEs", method = RequestMethod.POST)
    public Result<String> saveUserEs(@RequestParam String account,@RequestParam String pwd) {
        try {
           XContentBuilder content=  XContentFactory.jsonBuilder()
                    .startObject()
                    .field("account",account)
                    .field("pwd", PwdUtil.generate(pwd))
                    .endObject();

            IndexResponse result= this.client.prepareIndex("user","users")
                    .setSource(content)
                    .get();

            return successGet(result.getId());

        }catch (IOException e){
            e.printStackTrace();
            return successGet(500);
        }


    }


    @RequestMapping(value = "updateUserEs", method = RequestMethod.POST)
    public Result<String> updateUserEs(@RequestParam String id,@RequestParam String account,@RequestParam String pwd) {
        UpdateRequest update = new UpdateRequest("user","users",id);
        try {
            XContentBuilder builder=  XContentFactory.jsonBuilder()
                    .startObject();
            if(StringUtils.isNotBlank(account)){
                builder.field("account",account);
            }
            if(StringUtils.isNotBlank(pwd)){
                builder.field("pwd",PwdUtil.generate(pwd));
            }
            builder.endObject();
            update.doc(builder);

        }catch (IOException e){
            e.printStackTrace();
            return successGet(500);
        }

        try {
            UpdateResponse result= this.client.update(update).get();
            return successGet();
        }catch (Exception e){
            e.printStackTrace();
            return successGet(500);
        }


    }

    @RequestMapping(value = "query", method = RequestMethod.POST)
    public Result<UserDTO> query(@RequestParam String author, @RequestParam String title,
                                 @RequestParam int gtWoedCount , @RequestParam Integer ltWordCount) {

        BoolQueryBuilder booleanQuery = QueryBuilders.boolQuery();
        if(author !=null){
            booleanQuery.must(QueryBuilders.matchQuery("author",author));
        }

        if(title !=null){
            booleanQuery.must(QueryBuilders.matchQuery("title",title));
        }
        //范围查询
        RangeQueryBuilder rangeQuery = QueryBuilders.rangeQuery("wordCount").from(gtWoedCount);

        if(ltWordCount !=null && ltWordCount >0 ){
            //小于
            rangeQuery.to(ltWordCount);
        }

        booleanQuery.filter(rangeQuery);

        SearchRequestBuilder builder = this.client.prepareSearch("user").setTypes("users")
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setQuery(booleanQuery)
        .setFrom(0)
        .setSize(10);

        SearchResponse response = builder.get();
        for (SearchHit hit : response.getHits()){
                hit.getSourceAsMap();
        }
        return null;
    }


    @RequestMapping(value = "createOrder", method = RequestMethod.GET)
    public Result createOrder(@RequestParam Long id) {
        userService.createOrder(id);
        return successCreated();
    }

}
