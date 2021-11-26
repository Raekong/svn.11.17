package edu.nuist.ojs.baseinfo.controller;

import com.alibaba.fastjson.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.nuist.ojs.baseinfo.entity.Journal;
import edu.nuist.ojs.baseinfo.entity.JournalService;
import edu.nuist.ojs.baseinfo.entity.JournalSettingService;
import edu.nuist.ojs.baseinfo.entity.Publisher;
import edu.nuist.ojs.baseinfo.entity.PublisherService;

@RestController
public class ServiceController {
    @Autowired
    private PublisherService pService;

    @Autowired
    private JournalSettingService jService;

    @Autowired
    private JournalService journalService;

    @RequestMapping("/getDefaultEmailConfigForJournal")
    public JSONObject getEmailConfig(@RequestAttribute long id){ //ID 是JOURNAL ID,如果没有，则去找出版社的邮箱配置
       JSONObject obj = getEmailConfig( id,  false);
       if( obj == null ){
           long pid = journalService.findById(id).getPid();
           return getEmailConfig(pid, true);
       }
       return obj;
    }

    @RequestMapping("/getEmailConfig")
    public JSONObject getEmailConfig(@RequestAttribute long id,  @RequestAttribute boolean isPublish){
       

        if( isPublish ){
            JSONObject pub = new JSONObject();
            Publisher p = pService.findById(id);
            pub.put("host", p.getHost());
            pub.put("port", p.getPort());
            pub.put("senderName", p.getEmailSender());
            pub.put("account", p.getEmailAddress());
            pub.put("password", p.getPassword());
            return pub;
        }

        try{
            JSONObject journal = new JSONObject();
            String json = jService.findByJournalIdAndConfigPoint(id, "masthead").getConfigContent();
            JSONObject tmp =  JSONObject.parseObject(json);
            if( tmp.getString("host") == null ) return null;
            journal.put("host", tmp.getString("host"));
            journal.put("port", tmp.getIntValue("port"));
            journal.put("senderName", tmp.getString("emailsender"));
            journal.put("account", tmp.getString("email"));
            journal.put("password",tmp.getString("password"));
            return journal;
        }catch(Exception e){
            Journal j = journalService.findById(id);
            JSONObject pub = new JSONObject();
            Publisher p = pService.findById(j.getPid());
            pub.put("host", p.getHost());
            pub.put("port", p.getPort());
            pub.put("senderName", p.getEmailSender());
            pub.put("account", p.getEmailAddress());
            pub.put("password", p.getPassword());
            return pub;

        }
        

    }

}
