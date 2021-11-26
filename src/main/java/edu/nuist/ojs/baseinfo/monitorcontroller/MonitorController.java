package edu.nuist.ojs.baseinfo.monitorcontroller;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.nuist.ojs.baseinfo.entity.JournalSectionService;
import edu.nuist.ojs.baseinfo.entity.JournalService;
import edu.nuist.ojs.baseinfo.entity.PaymentService;
import edu.nuist.ojs.baseinfo.entity.SimilarCheck;
import edu.nuist.ojs.baseinfo.entity.SimilarCheckService;
import edu.nuist.ojs.baseinfo.entity.UserRoleRelationService;
import edu.nuist.ojs.baseinfo.entity.article.ArticleAuthor;
import edu.nuist.ojs.baseinfo.entity.article.ArticleEditorBoard;
import edu.nuist.ojs.baseinfo.entity.article.ArticleEditorBoardService;
import edu.nuist.ojs.baseinfo.entity.article.ArticleService;
import edu.nuist.ojs.baseinfo.entity.review.ReviewAction;
import edu.nuist.ojs.baseinfo.entity.review.ReviewActionService;
import edu.nuist.ojs.baseinfo.monitorentity.ArticleInfos;
import edu.nuist.ojs.baseinfo.monitorentity.CopyeditInfo;
import edu.nuist.ojs.baseinfo.monitorentity.PaymentInfo;
import edu.nuist.ojs.baseinfo.monitorentity.ReviewInfo;
import edu.nuist.ojs.baseinfo.monitorentity.ReviewRoundInfo;
import edu.nuist.ojs.baseinfo.monitorentity.Service_ArticleInfo;
import edu.nuist.ojs.baseinfo.monitorentity.Service_Copyedit;
import edu.nuist.ojs.baseinfo.monitorentity.Service_Payment;
import edu.nuist.ojs.baseinfo.monitorentity.Service_Review;
import edu.nuist.ojs.baseinfo.monitorentity.Service_Similar;
import edu.nuist.ojs.baseinfo.monitorentity.ShowSetting;
import edu.nuist.ojs.baseinfo.monitorentity.ShowSettingService;
import edu.nuist.ojs.baseinfo.monitorentity.SimilarCheckInfo;

@RestController
public class MonitorController {
    @Autowired
    ShowSettingService sService;
    
    @RequestMapping("/monitor/user/getSettingForUser")
    public JSONObject getSetting(@RequestAttribute long uid){
        ShowSetting ss = sService.findByUid(uid);
        if( ss == null ) return null;
        return JSONObject.parseObject(  sService.findByUid(uid).getSettingjson() );
    }

    @RequestMapping("/monitor/user/saveSettingForUser")
    public Object saveSetting(@RequestAttribute long uid, @RequestAttribute String jsonString){

        ShowSetting ss = sService.findByUid(uid);
        if( ss != null ){
            ss.setSettingjson(jsonString);
        }else{
            ss = ShowSetting.builder().uid(uid).settingjson(jsonString).build();
        }
        sService.save(ss);
        return JSONObject.toJSON(ss);
    }

    private String getFormatDate(long date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }
    //要求能求解每篇文章相对于用户的权限


    @Autowired
    private JournalService jService;
    @Autowired
    private ArticleService aService;
    @Autowired
    private JournalSectionService jsService;
    @Autowired
    private Service_ArticleInfo aiService;
    @Autowired
    private Service_Review rService;
    @Autowired
    private Service_Payment pService;
    @Autowired
    private Service_Similar ssService;
    @Autowired
    private Service_Copyedit cService;

    @Autowired
    private ReviewActionService raService; 

    @Autowired
    private UserRoleRelationService urService; 

    @Autowired
    private SimilarCheckService smService; 

    @Autowired
    private PaymentService pmService; 

    @Autowired
    private ArticleEditorBoardService aceService; 

    //取得一个用户对于某文章数据的访问权限
    public JSONObject getEditorRole(List<ArticleEditorBoard> aebs){
        JSONObject rst = new JSONObject();
        rst.put("editor", false);
        rst.put("similarcheck", false);
        rst.put("financial", false);
        rst.put("copyeditor", false);
        rst.put("perreviewer", false);
        for(ArticleEditorBoard aeb: aebs){
            if(aeb.getRoleId() == 5 ) rst.put("editor", true);
            if(aeb.getRoleId() == 10 ) rst.put("similarcheck", true);
            if(aeb.getRoleId() == 7 ) rst.put("financial", true);
            if(aeb.getRoleId() == 9 ) rst.put("copyeditor", true);
            if(aeb.getRoleId() == 11 ) rst.put("perreviewer", true);
        }

        return rst;
    }

    //彻底的完成了一次过滤，这次过滤主要是将用户对于每一篇文章的访问权限都限制死了
    public void fillInfos(
        List<ArticleInfos> ais,
        List<ReviewInfo> ris,
        List<PaymentInfo> pis,
        List<CopyeditInfo> cis,
        List<SimilarCheckInfo> sis,
        long aid,
        long uid,
        JSONObject obj
    ){
        boolean isOffice = urService.isOffice(uid, aid);
        List<ArticleEditorBoard> aebs = aceService.findByAidAndUid(aid, uid);
        JSONObject rolesForArticle = getEditorRole(aebs);

        ais.forEach(a->{
            if( a.getAid() == aid){
                obj.put("article", a);
            }
        });

        if( isOffice || rolesForArticle.getBooleanValue("editor")){
            ris.forEach(a->{
                if( a.getAid() == aid){
                    a.getLastRound();
                    obj.put("review", a);
                }
            });
        }

        if( isOffice || rolesForArticle.getBooleanValue("financial")){
            pis.forEach(a->{
                if( a.getAid() == aid){
                    a.setHistories(pmService.getPaymentsByAid(aid));
                    a.getDesc();
                    obj.put("payment", a);
                }
            });
        }

        if( isOffice || rolesForArticle.getBooleanValue("copyeditor")){
            cis.forEach(a->{
                if( a.getAid() == aid){
                    obj.put("copyedit", a);
                }
            });
        }

        if( isOffice || rolesForArticle.getBooleanValue("similarcheck")){
            sis.forEach(a->{
                if( a.getAid() == aid){
                    a.setRounds(smService.findByAid(aid));
                    a.getDesc();
                    obj.put("similar", a);
                }
            });
        }
        
    }
    
    @RequestMapping("/article/list/getMonitorData")
    public JSONArray getMonitorData(
        @RequestAttribute long uid,
        @RequestAttribute String ids
    ){
        List<Long> aids = JSONArray.parseArray(ids, Long.class);
        JSONArray arr = new JSONArray();

        List<ArticleInfos> ais = aiService.findByAIds(aids);
        List<ReviewInfo> ris = rService.findByAIds(aids);
        List<PaymentInfo> pis = pService.findByAIds(aids);
        List<CopyeditInfo> cis = cService.findByAIds(aids);
        List<SimilarCheckInfo> sis = ssService.findByAIds(aids);

        for(Long aid : aids){
            JSONObject obj = new JSONObject();
            obj.put("aid", aid);
            fillInfos(ais, ris, pis, cis, sis, aid, uid, obj);
            arr.add(obj);
        }
        return arr;
    }   

    @RequestMapping("/monitor/router")
    public String monitorRouter(@RequestAttribute JSONObject data){
        String endPoint = data.getString("endpoint");
        switch(endPoint){
            case "submit":
                aiService.save( createArticleInfos(data) );
                break;
            case "review-preview":
                aiService.save( updatePreview( data ));
                break;
            case "review":
                ReviewInfo ri = createReviewInfo( data ) ;
                rService.save( ri );
                aiService.updateStatus(ri.getAid(), ri.getStatus());
                break;
            case "reviewround":
                ReviewRoundInfo  r = getReviewRoundInfo(data);
                ReviewRoundInfo  rri = rService.findByAidAndSeq(r.getAid(), r.getSeq());
                if( rri != null ){
                    rri.setTotal(r.getTotal() + rri.getTotal());
                    JSONArray arr1 = JSONObject.parseArray( rri.getReviewers()); 
                    JSONArray arr2 = JSONObject.parseArray( r.getReviewers()); 
                    arr1.addAll(arr2);
                    rri.setReviewers( arr1.toJSONString() );
                    rService.saveRound(rri);
                }else{
                    rService.saveRound(r);
                }
                aiService.updateStatus(r.getAid(), data.getIntValue("status"));
                rService.updateStatus(r.getAid(), data.getIntValue("status"));
                break;
            case "updateoverdue":
                updateOverdue( data );
                break;
            case "reviewaction":
            case "reviewactionwithdraw":
                reviewAction(data);
                break;
            case "reviewdecision":
                reviewDecision(data);
                break;
            case "payment":
                createPayment(data);
                break;
            case "paid":
                paid(data);
            case "similarcheck-preview":
                aiService.save( updatePreview( data ));
                break;
            case "similarcheck":
                updateSimilarCheck(data);
                break;
            case "copyedit":
                updateCopyedit(data);
                break;
            case "copyedit-end":
                copyeditEnd(data);
                break;
        }
        return "";
    }

    public void copyeditEnd(JSONObject data){
        long aid = data.getLongValue("aid");
        CopyeditInfo ci = cService.findByAid(aid);
        if( ci == null){
            ci = CopyeditInfo.builder()
                 .aid(aid)
                 .pid(data.getLongValue("pid"))
                 .eemail(data.getString("eemail"))
                 .eid(data.getLongValue("edi"))
                 .ename(data.getString("ename"))
                 .startdate(getFormatDate(System.currentTimeMillis()))
                 .build();
            aiService.end(aid, getFormatDate(System.currentTimeMillis()));
            aiService.updateStatus(aid, ArticleInfos.copyedit_waiting_copyedit);
            cService.save(ci);
        }
        cService.updateEnd(aid, getFormatDate(System.currentTimeMillis()));
    }

    public void updateCopyedit(JSONObject data){
        long aid = data.getLongValue("aid");
        CopyeditInfo ci = cService.findByAid(aid);
        if( ci == null){
            ci = CopyeditInfo.builder()
                 .aid(aid)
                 .pid(data.getLongValue("pid"))
                 .eemail(data.getString("eemail"))
                 .eid(data.getLongValue("edi"))
                 .ename(data.getString("ename"))
                 .startdate(getFormatDate(System.currentTimeMillis()))
                 .build();
            aiService.updateStatus(aid, ArticleInfos.copyedit_waiting_copyedit);
            cService.save(ci);
        }
        
    }

    public void updateSimilarCheck(JSONObject data){
        long aid = data.getLongValue("aid");
        SimilarCheckInfo si = ssService.findByAid(aid);

        if(si == null){
            si = SimilarCheckInfo.builder()
                .aid(aid)
                .pid(data.getLongValue("pid"))
                .eemail(data.getString("eemail"))
                .eid(data.getLongValue("edi"))
                .ename(data.getString("ename"))
                .startdate(getFormatDate(System.currentTimeMillis()))
                .totalrounds(data.getIntValue("totalrun"))
                .build();
        }else{
            si.setTotalrounds(data.getIntValue("totalrun"));
        }
        aiService.updateStatus(aid, ArticleInfos.similarity_check_round);
        ssService.save(si);
    }

    public void paid(JSONObject data){
       long aid = data.getLongValue("aid");
       PaymentInfo pi = pService.findByAid(aid);

       int total = data.getIntValue("total");
       pi.setTotalpaid(pi.getTotalpaid()+total);
       pService.save(pi);
       aiService.updateStatus(aid, ArticleInfos.payment_paid);
    }

    public void createPayment(JSONObject data){
        long aid = data.getLongValue("aid");
        PaymentInfo p = PaymentInfo.builder()
                        .aid(data.getLongValue("aid"))
                        .pid(data.getLongValue("pid"))
                        .eid(data.getLongValue("eid"))
                        .eemail(data.getString("eemail"))
                        .ename(data.getString("ename"))
                        .startdate(getFormatDate(System.currentTimeMillis()))
                        .build();
        pService.save(p);
        aiService.updateStatus(aid, ArticleInfos.payment_waiting);
    }

    public void reviewDecision(JSONObject data){
        String type = data.getString("type");
        long aid = data.getLongValue("aid");
        int rid = data.getIntValue("rid");
        switch(type){
            case "revision" :
                rService.updateResult(aid, rid, ReviewRoundInfo.REVISION);
                aiService.updateStatus(aid, ArticleInfos.review_decision_revision);
                break;
            case "accept":
                rService.updateResult(aid, rid, ReviewRoundInfo.ACCEPT);
                aiService.updateStatus(aid, ArticleInfos.review_decision_accept);
                break;
            case "decline":
                rService.updateResult(aid, rid, ReviewRoundInfo.DECLINE);
                aiService.updateStatus(aid, ArticleInfos.review_decision_decline);
                break;
        }
        //更新论文的状态
        
    }

    public void resetReviewRoundStatis( long raid ){
        ReviewAction ra = raService.findById(raid);
        updateReviewRound(ra.getArticleId(), (int)ra.getRoundId());
    }

    public void reviewAction(JSONObject data){
        resetReviewRoundStatis( data.getLongValue("raid") );
    }

    public void updateReviewRound( long aid, int roundId){
        ReviewRoundInfo ri = rService.findByAidAndSeq(aid, roundId);
        List<ReviewAction> actions = raService.findByArticleIdAndRoundId(aid, roundId);
        int[] rst = new int[5];
        for(ReviewAction action:actions){
            System.out.println( action.getCurstate() );
            if(action.getCurstate() == ReviewAction.COMPLETE){
               rst[0] += 1;
            }else if(action.getCurstate() == ReviewAction.REVIEW){
               rst[1] += 1;
            }else if(action.getCurstate() == ReviewAction.RESPONSEOVERDUE || action.getCurstate() == ReviewAction.REVIEWOVERDUE){
               rst[2] += 1;
            }else if(action.getCurstate() == ReviewAction.REJECT){
               rst[3] +=1 ;
            }    
            rst[4]++;
        }   
        ri.setCompleted(rst[0]); ri.setReviewing(rst[1]); ri.setOverdue(rst[2]); ri.setDecline(rst[3]);
        ri.setTotal(rst[4]);
       
        
        rService.saveRound(ri);
    }

    public void updateOverdue(JSONObject data){
        JSONArray raids = data.getJSONArray("raids");
        for(int i=0; i<raids.size(); i++){
            String[] param = raids.getString(i).split(",");
            updateReviewRound( Long.valueOf(param[0]), Integer.valueOf(param[1]));
        }
    }

    public ReviewRoundInfo getReviewRoundInfo(JSONObject data){
        ReviewRoundInfo rri = ReviewRoundInfo.builder()
                             .aid(data.getLongValue("aid"))
                             .pid(data.getLongValue("pid"))
                             .reviewers(data.getString("reviewers"))
                             .seq(data.getIntValue("seq"))
                             .total(data.getIntValue("total"))
                             .build();
                              
        return rri;
    }

    public ReviewInfo createReviewInfo(JSONObject data){
        ReviewInfo ri = ReviewInfo.builder()
                        .aid(data.getLongValue("aid"))
                        .pid(data.getLongValue("pid"))
                        .eid(data.getLongValue("eid"))
                        .eemail(data.getString("eemail"))
                        .ename(data.getString("ename"))
                        .totalrounds(data.getIntValue("totalrounds"))
                        .startdate(getFormatDate(data.getLongValue("startdate")))
                        .status(data.getIntValue("status"))
                        .build();
        return ri;
                        
    }


    public ArticleInfos updatePreview(JSONObject data){
        ArticleInfos ai = aiService.findByAid(data.getLongValue("aid"));
        ai.setEid(data.getLongValue("eid"));
        ai.setEemail(data.getString("eemail"));
        ai.setEname(data.getString("ename"));
        ai.setSindex(data.getIntValue("sindex"));
        return ai;
    }

    public ArticleInfos createArticleInfos(JSONObject data){
        String jtitle = jService.findById(data.getLongValue("jid")).getTitle();
        String stitle = jsService.findById(data.getLongValue("sid")).getTitle();
        List<ArticleAuthor> authors = aService.findById( data.getLongValue("aid") ).getAuthors(); 

        JSONArray arr = new JSONArray();
        for(ArticleAuthor aa : authors){
            JSONObject obj = new JSONObject();
            obj.put("name", aa.getName());
            obj.put("email", aa.getEmail());
            arr.add(obj);
        }
        ArticleInfos ai = ArticleInfos.builder()
            .aid(data.getLongValue("aid"))
            .pid(data.getLongValue("pid"))
            .jid(data.getLongValue("jid"))
            .jtitle(jtitle)
            .sid(data.getLongValue("sid"))
            .stitle(stitle)
            .title(data.getString("title"))
            .subdate(getFormatDate(data.getLongValue("subdate")))
            .subid(data.getLongValue("uid"))
            .subemail(data.getString("subemail"))
            .subname(data.getString("subname"))
            .sindex(data.getIntValue("sindex"))
            .jtitle(jtitle)
            .authors(JSONObject.toJSONString(arr))
            .build();

        return ai;
    }
    


}
