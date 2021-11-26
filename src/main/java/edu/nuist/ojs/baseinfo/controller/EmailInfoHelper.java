package edu.nuist.ojs.baseinfo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import edu.nuist.ojs.baseinfo.entity.article.Article;
import edu.nuist.ojs.baseinfo.entity.article.ArticleService;
import edu.nuist.ojs.baseinfo.entity.JournalService;
import edu.nuist.ojs.baseinfo.entity.PublisherService;
import edu.nuist.ojs.baseinfo.entity.UserService;
import edu.nuist.ojs.baseinfo.entity.JournalSettingService;

@RestController
public class EmailInfoHelper {
    @Autowired
    private JournalService jService;

    @Autowired
    PublisherService publisherService;

    @Autowired
    UserService userService;

    @Autowired
    private ArticleService aService;
    
    @Autowired
    private JournalSettingService jsService;
     
    @RequestMapping("/email/getEmailVariables")
    public JSONObject getEmailVariables(
        @RequestAttribute String paraTagInJsonStr
    ){
        JSONObject param = JSONObject.parseObject(paraTagInJsonStr);
        JSONObject rst = new JSONObject();

        long paramId = -1;
        if( param.get("p") != null ){
            paramId = param.getLongValue("p");
            rst.put("p", publisherService.findById(paramId));
        }

        if( param.get("j") != null){
            paramId = param.getLongValue("j");
            rst.put("j", jService.findById(paramId));
        }

        if( param.get("a") != null){
            paramId = param.getLongValue("a");
            Article a = aService.findById(paramId);
            rst.put("a", a);
            rst.put("submitor", userService.findByUserId(a.getSubmitorId()));
        }

        if( param.get("js") != null){
            paramId = param.getLongValue("js");
            rst.put("js", jsService.findByJournalId(paramId));
        }

        return rst;
    }

}
