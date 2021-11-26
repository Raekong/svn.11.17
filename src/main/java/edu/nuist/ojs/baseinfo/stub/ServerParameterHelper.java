package edu.nuist.ojs.baseinfo.stub;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;

import edu.nuist.ojs.baseinfo.entity.Journal;
import edu.nuist.ojs.baseinfo.entity.JournalSection;
import edu.nuist.ojs.baseinfo.entity.JournalSetting;
import edu.nuist.ojs.baseinfo.entity.Link;
import edu.nuist.ojs.baseinfo.entity.Payment;
import edu.nuist.ojs.baseinfo.entity.Publisher;
import edu.nuist.ojs.baseinfo.entity.Role;
import edu.nuist.ojs.baseinfo.entity.SimilarCheck;
import edu.nuist.ojs.baseinfo.entity.User;
import edu.nuist.ojs.baseinfo.entity.UserRoleRelation;
import edu.nuist.ojs.baseinfo.entity.article.ArticleFile;
import edu.nuist.ojs.baseinfo.entity.article.ArticleHistory;

public class ServerParameterHelper {
    public static HttpServletRequest exec(JSONObject parameter, String api, HttpServletRequest request){
        switch (api){
            case "/publish/regist":{
                Publisher publisher=parameter.getObject("publisher",Publisher.class);
                User user=parameter.getObject("user",User.class);
                request.setAttribute("user",user);
                request.setAttribute("publisher",publisher);
                break;
            }
            case "/publish/update":{
                Publisher publisher=parameter.getObject("publisher",Publisher.class);
                request.setAttribute("publisher",publisher);
                break;
            }
            case "/user/regist":{
                User user= parameter.getObject("u", User.class);
                request.setAttribute("user",user);
                break;
            }
            case "/user/update":{
                User user= parameter.getObject("u", User.class);
                request.setAttribute("u",user);
                break;
            }
            case "/journal/save":{
                Journal journal =parameter.getObject("journal", Journal.class);
                request.setAttribute("journal",journal);
                break;
            }
            case "/journal/setting":{
                JournalSetting setting =parameter.getObject("setting", JournalSetting.class);
                request.setAttribute("setting", setting);
                break;
            }
            case "/journal/section/save":{
                JournalSection setting =parameter.getObject("section", JournalSection.class);
                request.setAttribute("section", setting);
                break;
            }
            case "/journal/role/save":{
                Role role = parameter.getObject("role", Role.class);
                request.setAttribute("role",role);
                break;
            }
            case "/journal/userRoleRelation/save":{
                UserRoleRelation userRoleRelation = parameter.getObject("userRoleRelation",UserRoleRelation.class);
                request.setAttribute("userRoleRelation",userRoleRelation);
                break;
            }
            case "/link/save":{
                Link l = parameter.getObject("link", Link.class);
                request.setAttribute("link", l);
                break;
            }
            case "/article/history/save":{
                ArticleHistory l = parameter.getObject("ah", ArticleHistory.class);
                request.setAttribute("ah", l);
                break;
            }
            case "/similarCheck/save":{
                SimilarCheck similarCheck = parameter.getObject("similarCheck",SimilarCheck.class);
                request.setAttribute("similarCheck",similarCheck);
                break;
            }
            case "/article/file/save":{
                ArticleFile file = parameter.getObject("file", ArticleFile.class);
                request.setAttribute("file", file);
                break;
            }
            case "/payment/save":{
                Payment p = parameter.getObject("payment", Payment.class);
                request.setAttribute("payment", p);
                break;
            }
            default:{
                for (String key : parameter.keySet()) {
                    request.setAttribute(key, parameter.get(key));
                }
            }
        }
        
        return request;
    }
}