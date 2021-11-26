package edu.nuist.ojs.baseinfo.controller;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.hutool.http.HttpRequest;
import edu.nuist.ojs.baseinfo.entity.JournalSectionService;
import edu.nuist.ojs.baseinfo.entity.JournalService;
import edu.nuist.ojs.baseinfo.entity.LinkService;
import edu.nuist.ojs.baseinfo.entity.Link;
import edu.nuist.ojs.baseinfo.entity.User;
import edu.nuist.ojs.baseinfo.entity.UserRoleRelationService;
import edu.nuist.ojs.baseinfo.entity.UserService;
import edu.nuist.ojs.baseinfo.entity.article.Article;
import edu.nuist.ojs.baseinfo.entity.article.ArticleAuthor;
import edu.nuist.ojs.baseinfo.entity.article.ArticleDisscusion;
import edu.nuist.ojs.baseinfo.entity.article.ArticleDisscusionService;
import edu.nuist.ojs.baseinfo.entity.article.ArticleEditorBoard;
import edu.nuist.ojs.baseinfo.entity.article.ArticleEditorBoardService;
import edu.nuist.ojs.baseinfo.entity.article.ArticleFile;
import edu.nuist.ojs.baseinfo.entity.article.ArticleHistory;
import edu.nuist.ojs.baseinfo.entity.article.ArticleHistoryService;
import edu.nuist.ojs.baseinfo.entity.article.ArticleReviewer;
import edu.nuist.ojs.baseinfo.entity.article.ArticleService;
import edu.nuist.ojs.baseinfo.entity.article.ArticleUser;
import edu.nuist.ojs.baseinfo.entity.review.ReviewAction;
import edu.nuist.ojs.baseinfo.entity.review.ReviewActionService;
import edu.nuist.ojs.baseinfo.entity.review.ReviewRemindCount;
import edu.nuist.ojs.baseinfo.entity.review.ReviewRemindCountService;
import edu.nuist.ojs.baseinfo.entity.review.ReviewResult;
import edu.nuist.ojs.baseinfo.entity.review.ReviewResultService;
import edu.nuist.ojs.baseinfo.entity.review.Reviewer;
import edu.nuist.ojs.baseinfo.entity.review.ReviewerService;


@RestController
public class ArticleController {

    @Autowired
    private ArticleService aService;


    @Autowired
    private ArticleHistoryService ahService;
    
    @Autowired
    private JournalService jService;

    @Autowired
    private JournalSectionService sService; 

    @Autowired
    private ArticleEditorBoardService aebService; 

    @Autowired
    private UserService userService;

    @Autowired
    private UserRoleRelationService urrService;
    
    @RequestMapping("/article/getById")
    public String getById(
        @RequestAttribute long aid
    ){
        return JSON.toJSONString(aService.findById(aid));
    }   

    

    @RequestMapping("/article/list/getByType")
    public String getListByType(
        @RequestAttribute String type,
        @RequestAttribute long uid,
        @RequestAttribute int  page,
        @RequestAttribute int size
    ){
        Pageable pageRequest = PageRequest.of(page-1,size);

        Page<Article> articles = null; 
        switch(type){
            case "author":
                articles = aService.listByAuthor(pageRequest, uid);
                break;
            case "contributor":
                articles = aService.listByContributor(pageRequest, uid );
                break;
        }

        JSONObject rst = JSONObject.parseObject(JSONObject.toJSONString(articles) );
        if( type.equals("editor")){
            Page<ArticleUser> aus = aService.listByEditor(pageRequest, uid );
            rst = JSONObject.parseObject(JSONObject.toJSONString(aus) );
        }else{
            JSONArray arr = JSONObject.parseObject(  JSONObject.toJSONString(articles) ).getJSONArray("content");
            for(int i=0; i<arr.size(); i++){
                JSONObject article = arr.getJSONObject(i);
                long jid = article.getLongValue("jid");
                long sid = article.getLongValue("sectionId");
                article.put("journal", jService.findById(jid).getAbbreviation());
                article.put("section", sService.findById(sid).getTitle());
                article.put("status", ahService.getLastStatusById(article.getLongValue("id"))); //添加额外信息
            }
            rst.put("content", arr);
        }
        
        return rst.toJSONString();
    }   

    @RequestMapping("/article/getSectionIdByAid")
    public User getSectionUser(@RequestAttribute long aid){
        return userService.findUserBySectionId(aid);
    }

    @RequestMapping("/article/changeJournal")
    public String changeJournal(@RequestAttribute long aid, @RequestAttribute long jid, @RequestAttribute long sid){
        aService.changeJournal(aid, jid, sid);
        return "";
    }

    @RequestMapping("/submit/do")
    public String submit(
        @RequestAttribute String json,
        @RequestAttribute long jid,
        @RequestAttribute long uid,
        @RequestAttribute long pid
    ){
        JSONObject data = JSONObject.parseObject(json);
        Article article = JSONObject.toJavaObject(data, Article.class);
        
        article.setJid(jid); article.setPid(pid); article.setSubmitorId(uid);

        List<ArticleReviewer> reviewers = new LinkedList<>();
        JSONArray arr = data.getJSONArray("reviewers");
        for( int i=0; i<arr.size(); i++){
            ArticleReviewer reviewer = JSONObject.toJavaObject(arr.getJSONObject(i), ArticleReviewer.class);
            reviewers.add(reviewer);
        }
        article.setReviewers(reviewers);


        List<ArticleAuthor> authors = new LinkedList<>();
        arr = data.getJSONArray("authors");
        for( int i=0; i<arr.size(); i++){
            ArticleAuthor author = JSONObject.toJavaObject(arr.getJSONObject(i), ArticleAuthor.class);
            authors.add(author);
        }
        article.setAuthors(authors);


        List<ArticleFile> files = new LinkedList<>();
        arr = data.getJSONArray("uploadFiles");
        for( int i=0; i<arr.size(); i++){
            ArticleFile af = JSONObject.toJavaObject(arr.getJSONObject(i), ArticleFile.class);
            af.setVersion("SUBMIT-0");
            files.add(af);
        }
        article.setFiles(files);

        return JSONObject.toJSONString( aService.save(article) );
    }

    @RequestMapping("/article/history/save")
    public String historySave(
        @RequestAttribute ArticleHistory ah 
    ){
        ;
        return JSONObject.toJSONString( ahService.save(ah));
    }

    @RequestMapping("/article/getLastStatusById")
    public ArticleHistory getLastStatusById(
        @RequestAttribute long aid
    ){
        return ahService.getLastStatusById(aid);
    }

    @RequestMapping("/article/history/getTabs")
    public String getTabs(
        @RequestAttribute long aid
    ){
        List<Object> tabs = ahService.getTabs(aid);
        return JSONObject.toJSONString(tabs);
    }

    @RequestMapping("/article/history/lastHistoryInRound")
    public ArticleHistory lastHistoryInRound(
        @RequestAttribute long aid, 
        @RequestAttribute int round
    ){
        return ahService.lastHistoryInRound(aid,  round);
    }

    @RequestMapping("/article/history/getHistoryByAid")
    public String getHistoryByAid(
        @RequestAttribute long aid
    ){
        return JSONObject.toJSONString(ahService.findByAId(aid));
    }

    @RequestMapping("/article/history/getLastHistory")
    public ArticleHistory getLastHistory(
        @RequestAttribute long aid, 
        @RequestAttribute String status, 
        @RequestAttribute int round
    ){
        return ahService.findLastByAidAndStatusAndRound(aid, status, round);
    }

    @RequestMapping("/article/history/getById")
    public ArticleHistory getHistoyById(
        @RequestAttribute long ahid
    ){
        return ahService.findById(ahid);
    }

    @RequestMapping("/article/history/get")
    public ArticleHistory getHistoy(
        @RequestAttribute long aid, 
        @RequestAttribute String status, 
        @RequestAttribute int round
    ){
        return ahService.findByAidAndStatusAndRound(aid, status, round);
    }

    @RequestMapping("/article/history/getHistoryByAidandFlow")
    public String getHistoy(
        @RequestAttribute long aid, 
        @RequestAttribute String stage
    ){
        return JSONObject.toJSONString(ahService.findByAidAndWorkflow(aid, stage));
    }

    @RequestMapping("/article/history/getNextHistory")
    public ArticleHistory getNextHistory(
        @RequestAttribute long aid, 
        @RequestAttribute String status, 
        @RequestAttribute int round
    ){
        return ahService.getNextHistory(aid, status, round);
    }

    @RequestMapping("/article/history/getWorkflowHasPreview")
    public String getWorkflowHasPreview(
        @RequestAttribute long aid
    ){
        ArticleHistory ah = ahService.getWorkflowHasPreview(aid);
        if( ah == null) return "";
        else return ah.getWorkflow();
    }

    @RequestMapping("/article/file/canReupload")
    public boolean canReupload(
        @RequestAttribute long aid,
        @RequestAttribute String version,
        @RequestAttribute String filetype
    ){
        List<ArticleFile> afs = aService.findByAidAndVersionAndFileType(aid, version, filetype);
        return afs == null || afs.size()==0;
    }



    @RequestMapping("/article/file/del")
    public String delFile(
        @RequestAttribute long fid
    ){
        aService.delFile(fid);
        return "";
    }

    @RequestMapping("/article/file/save")
    public ArticleFile saveArticleFile(
        @RequestAttribute ArticleFile file
    ){
        return aService.saveFile(file);
    }

    @RequestMapping("/article/file/findByAidAndVersionAndFileType")
    public ArticleFile getFileByAidAndVersionAndFileType(
        @RequestAttribute long aid,
        @RequestAttribute String version,
        @RequestAttribute String fileType
    ){
        
        return aService.findByAidAndVersionAndFileType( aid,  version,  fileType).get(0);
    }


    @RequestMapping("/article/file/getFileForReviewRound")
    public String getFileForReviewRound(
        @RequestAttribute long aid,
        @RequestAttribute int rid
    ){       
        List<ArticleFile> files=aService.findFilesByAidAndVersion(aid, "REVIEW-" + rid);
        return JSONObject.toJSONString(files);
    }

    @RequestMapping("/article/file/getFileForCopyeditRound")
    public String getFileForCopyeditRound(
        @RequestAttribute long aid,
        @RequestAttribute int rid
    ){       
        List<ArticleFile> files=aService.findFilesByAidAndVersion(aid, "COPYEDIT-" + rid);
        return JSONObject.toJSONString(files);
    }

    @RequestMapping("/article/file/upload")
    public String uploadFile(
        @RequestAttribute String filetype, 
        @RequestAttribute String originName, 
        @RequestAttribute String innerId,
        @RequestAttribute long aid,
        @RequestAttribute String version
    ){
        ArticleFile af = ArticleFile.builder()
            .version(version).aid(aid).originName(originName).fileType(filetype).innerId(innerId).build();
        aService.saveFile(af);
        return "";
    }

    @RequestMapping("/article/board/save")
    public String saveBoardEditor(
        @RequestAttribute long aid,
        @RequestAttribute long rid,
        @RequestAttribute long uid,
        @RequestAttribute boolean decision
    ){
        ArticleEditorBoard aeb = ArticleEditorBoard.builder().aid(aid).roleId(rid).uid(uid).decision(decision).build();
        aebService.save(aeb);
        return "";
    }

    @RequestMapping("/article/board/del")
    public String delBoardEditor(
        @RequestAttribute long aid,
        @RequestAttribute long rid,
        @RequestAttribute String email
    ){
        User u = userService.findEditorByAidAndEmail(aid, email);
        aebService.delEditorByAidAndRidAndEmail(aid, rid, u.getUserId());
        return "";
    }

    //取出一篇文章所有的EDITOR，并返回编辑信息
    @RequestMapping("/article/board/getBoardBydAid")
    public String getBoardBydAid(
        @RequestAttribute long aid
    ){
        List<ArticleEditorBoard> aebs = aebService.findByAid( aid);
        List<User> editors = userService.findEditorBoardByAid(aid);
        User sectionEditor = userService.findUserBySectionId( aid);

        List<JSONObject> rst = new LinkedList<>();
        for(ArticleEditorBoard aeb : aebs){
            JSONObject tmp = new JSONObject();
            tmp.put("uid", aeb.getUid());
            if( aeb.getRoleId() == 1 ){
                tmp.put("role", "manager");
                tmp.put("decision", true);
                tmp.put("define", "system");
            }else if( aeb.getRoleId() == 5){
                tmp.put("role", "section");
                tmp.put("decision", aeb.isDecision());
                if( sectionEditor.getUserId() == aeb.getUid()){
                    tmp.put("define", "system");
                }else{
                    tmp.put("define", "invite");
                }
            }else if( aeb.getRoleId() == 10){
                tmp.put("role", "similar");
                tmp.put("decision", aeb.isDecision());
                tmp.put("define", "system");
            }else if( aeb.getRoleId()== 11 ){
                tmp.put("role", "prereviewer");
                tmp.put("decision", false);
                tmp.put("define", "system");
            }else if( aeb.getRoleId()== 7 ){
                tmp.put("role", "financial");
                tmp.put("decision", false   );
                tmp.put("define", "system");
            }else if( aeb.getRoleId()== 9 ){
                tmp.put("role", "copyeditor");
                tmp.put("decision", false   );
                tmp.put("define", "system");
            }
            for(User editor: editors){
                if( aeb.getUid() == editor.getUserId()){
                    tmp.put("name", editor.getUsername());
                    tmp.put("email", editor.getEmail());
                }
            }

            rst.add(tmp);
        }

        return JSONObject.toJSONString(rst);
    }



    //取出一篇文章中用户的角色信息并判断
    @RequestMapping("/article/board/getRolesForUser")
    public JSONObject getRolesForUser(
        @RequestAttribute long aid,
        @RequestAttribute long uid
    ){
        List<ArticleEditorBoard> aebs = aebService.findByAidAndUid(aid, uid);
        User sectionEditor = userService.findUserBySectionId( aid);
        JSONObject obj = new JSONObject();
        //是不是期刊OFFICE
        obj.put("manager", urrService.isOffice(uid, aid));
        //在这个论文TEAM中承担的角色
        obj.put("boards", aebs);
        //是不是期刊栏目责任编辑
        obj.put("r.review.responsingeditor", uid==sectionEditor.getUserId());
        //是不是作者
        obj.put("r.author", aService.isAuthor(uid, aid, userService.findByUserId(uid).getEmail())!=null);
        return obj;
    }

    @RequestMapping("/article/file/getArticleFilesByAHId")
    public String getArticleFilesByAHId(
        @RequestAttribute long ahid
    ){
        ArticleHistory ah = ahService.findById(ahid);
        List<ArticleFile> rst = aService.findFilesByAidAndVersion(ah.getAid(), ah.getFileVersion());

        return JSONObject.toJSONString(rst);
    }

    @RequestMapping("/article/file/getArticleFileById")
    public ArticleFile getArticleFileById(
        @RequestAttribute long fid
    ){
        return aService.findFileById(fid);
    }

    @RequestMapping("/article/file/getArticleFilesByAidAndVersion")
    public String getArticleFilesByAidAndVersion(
        @RequestAttribute long aid,
        @RequestAttribute String version
    ){
        List<ArticleFile> rst = aService.findFilesByAidAndVersion(aid, version);
        return JSONObject.toJSONString(rst);
    }


    @Autowired
    private ArticleDisscusionService disscusionService; 

    @RequestMapping("/article/disscusion/save")
    public String getArticleFilesByAHId(
        @RequestAttribute long aid,
        @RequestAttribute long rid,
        @RequestAttribute long sendid,
        @RequestAttribute String sendEmail,
        @RequestAttribute long receId,
        @RequestAttribute long msgid,
        @RequestAttribute int type
    ){

        ArticleDisscusion dis = ArticleDisscusion.builder()
                .aid(aid)
                .rid(rid)
                .sendId(sendid)
                .sendEmail(sendEmail)
                .receId(receId)
                .msgId(msgid)
                .type(type).
                build();
        disscusionService.save(dis);

        return JSONObject.toJSONString(dis);
    }
    
    @RequestMapping("/article/disscusion/get")
    public String getArticleFilesByAHId(
        @RequestAttribute long aid,
        @RequestAttribute long rid

    ){
        return JSONObject.toJSONString(disscusionService.findByRidAndAid( rid, aid) );
    }


    @Autowired
    private ReviewerService reviewerService;

    @Autowired
    private ReviewActionService rService;

    @Autowired
    private LinkService lService;

    @Autowired
    private ReviewResultService rrService; 

    @Autowired
    private ReviewRemindCountService rrcService; 

    @RequestMapping("/article/review/getSuggestReviewer")
    public String getSuggestReviewer(@RequestAttribute long aid){
        return JSONObject.toJSONString(aService.findByAid(aid));
    }

    @RequestMapping("/article/review/getLastRoundReviewer")
    public String getLastRoundReviewer(@RequestAttribute long aid, @RequestAttribute long rid){
        List<Reviewer> r = reviewerService.findByAidAndRoundId(  aid,  rid-1);
        return JSONObject.toJSONString(r );
    }

    @RequestMapping("/article/review/saveReviewers")
    public String saveReviewers(
        @RequestAttribute long pid,
        @RequestAttribute JSONObject reviewer
    ){
        if( reviewerService.findByPidAndEmail(pid, reviewer.getString("email")) != null){
            return "";
        }

        Reviewer r = Reviewer.builder()
                    .email(reviewer.getString("email"))
                    .name(reviewer.getString("name"))
                    .affiliation(reviewer.getString("affiliation"))
                    .researchField(reviewer.getString("research"))
                    .pid(pid)
                    .build();

        reviewerService.save(r);
        return "";
    }

    @RequestMapping("/article/review/getReviewActionByAidAndRid")
    public String getReviewActionByAidAndRid(
        @RequestAttribute long aid, 
        @RequestAttribute int rid
    ){
        return JSONObject.toJSONString(rService.findByArticleIdAndRoundId(aid, rid));
    }

    @RequestMapping("/article/review/saveReviewLink")
    public String saveReviewLink(
        @RequestAttribute String link
    ){
        JSONObject obj = JSONObject.parseObject(link);
        lService.save(JSONObject.toJavaObject(obj, Link.class ));
        return "";
    }

    @RequestMapping("/article/review/getReviewLink")
    public Link getReviewLink(
        @RequestAttribute String md5
    ){
        return lService.findByMD5(md5);
    }

    @RequestMapping("/article/review/getReviewLinkByActionId")
    public Link getReviewLinkByActionI(
        @RequestAttribute long raid
    ){
        String para = "\"actionid\":\"" +  Long.valueOf(raid).toString();
        return lService.findByRaidLike(para);
    }

    @RequestMapping("/article/review/getReviewLinkMd5")
    public String getReviewLinkMd5(
        @RequestAttribute long raid
    ){
        String para = "\"actionid\":\"" +  Long.valueOf(raid).toString();
        return lService.findByRaidLike(para).getMD5();
    }

    @RequestMapping("/article/review/getReviewResult")
    public ReviewResult getReviewResult(
        @RequestAttribute long raid
    ){
        
        return rrService.findByReviewActionId(raid);
    }

    @RequestMapping("/article/review/getReviewActionById")
    public ReviewAction saveReviewActions(
        @RequestAttribute long actionid
    ){
        return rService.findById(actionid);
    }

    @RequestMapping("/article/review/getRemindTimes")
    public ReviewRemindCount getRemindTimes(
        @RequestAttribute long raid
    ){
        return rrcService.findByRaid(raid);
    }

    @RequestMapping("/article/review/saveReviewActionRemind")
    public ReviewRemindCount saveReviewActionRemind(
        @RequestAttribute long raid,
        @RequestAttribute int times,
        @RequestAttribute int pre
    ){
        SimpleDateFormat sf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        return rrcService.save(
            ReviewRemindCount.builder()
            .raid(raid)
            .timeStamp(sf.format(new Date()))
            .need(times)
            .preoid(pre)
            .build());
    }

    @RequestMapping("/article/review/updateRemindCount")
    public String updateRemindCount(
        @RequestAttribute long raid,
        @RequestAttribute boolean isSystem
    ){
        SimpleDateFormat sf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        rrcService.update( raid, sf.format(new Date()), isSystem);  
        return "";
       
    }

    @RequestMapping("/article/review/needRemind")
    public String needRemind(
    ){
        List<ReviewRemindCount> reviewRemindCounts = rrcService.getNeedRemindActions( );  
        if( reviewRemindCounts.size() == 0 ) return "[]";
        List<Long> rst = new LinkedList<>();
        for(ReviewRemindCount rrc : reviewRemindCounts){
            long raid = rrc.getRaid();
            ReviewAction ra = rService.findById(raid);
            if( ra.getCurstate() != ReviewAction.COMPLETE && ra.getCurstate() != ReviewAction.CLOSE){
                rst.add(ra.getId());
            }
        }
        return JSONObject.toJSONString(rst);
       
    }

    @RequestMapping("/article/review/completeNumByRaid")
    public String completeNumByRidAndAid(
        @RequestAttribute long raid
    ){
        ReviewAction ra = rService.findById(raid);
        List<ReviewAction> actions = rService.completeByAidAndRid(  ra.getArticleId(),  ra.getRoundId());
        return actions.size() + "";
            
    }

    @RequestMapping("/article/review/unCompleteActionByAidAndRid")
    public String unCompleteNumByRaid(
        @RequestAttribute long aid,
        @RequestAttribute long rid
    ){
        return  JSONObject.toJSONString(rService.unclosedActionByAidAndRid(  aid,  rid)) ;
    }

    @RequestMapping("/article/review/updateOverDue")
    public void updateOverDue(
        @RequestAttribute String date,
        HttpServletRequest request,
        HttpServletResponse response 
    ) throws ServletException, IOException{

        //为监控收集准备
        List<ReviewAction> actions = rService.getOverDueActions(date);
        //更新逾期的RA
        rService.updateOverDue( date, System.currentTimeMillis() );

        //发送到MONITOR
        HashSet<String> arr = new HashSet<String>();
        for(ReviewAction action: actions  ){
            arr.add( action.getArticleId() + "," + action.getRoundId());
        }

        JSONArray rounds = new JSONArray();
        for(String s : arr){
            rounds.add( s );
        }
        JSONObject obj = new JSONObject();
        obj.put("raids", rounds);
        obj.put("endpoint", "updateoverdue");
        request.setAttribute("data", obj);
        request.getRequestDispatcher("/monitor/router").forward(request, response);
    }


    @RequestMapping("/article/review/update")
    public String updateReviewActions(
        @RequestAttribute long raid,
        @RequestAttribute int status
    ){
        rService.update(raid, status, System.currentTimeMillis());
        return "";
    }

    @RequestMapping("/article/review/close")
    public String closeReviewActions(
        @RequestAttribute long raid
    ){
        rService.close(raid);
        return "";
    }

    @RequestMapping("/article/review/withdrawDecision")
    public void withdrawDecision(
        @RequestAttribute long aid,
        @RequestAttribute int rid
    ){
        ArticleHistory ah = ahService.lastHistoryInRound(aid, rid);
        ahService.deleteById(ah.getId());
    }

    @RequestMapping("/article/review/withdraw")
    public String withdrawReviewActions(
        @RequestAttribute long raid
    ){
        ReviewAction ra = rService.findById(raid);
        if( ra.isClosed() ){
            rService.unClose(raid);
        }else{
            rrService.delByReviewActionId(raid); //删除可能的结果，要不然会出现结果查询错误
            rService.withdrawComplete(raid);//删除REVIEWACTION中的结果
        }
        return "";
    }

    @RequestMapping("/article/review/submit")
    public String reviewSubmit(
        @RequestAttribute long raid,
        @RequestAttribute String authorRecom,
        @RequestAttribute String editorRecom,
        @RequestAttribute String fileJsonStr,
        @RequestAttribute int recommendType
    ){
        rrService.save(
            ReviewResult.builder()
                .reviewActionId(raid)
                .commendforAuthor(authorRecom)
                .commendforEditor(editorRecom)
                .filesStr(fileJsonStr)
                .recommendType(recommendType)
                .build());
        
        rService.complete(raid, ReviewAction.COMPLETE, recommendType, System.currentTimeMillis());
        return "";
    }

    @RequestMapping("/article/review/saveReviewActions")
    public long saveReviewActions(
        @RequestAttribute long pid, 
        @RequestAttribute long aid, 
        @RequestAttribute int rid,  //第几轮
        @RequestAttribute long uid, //操作者
        @RequestAttribute String files,
        @RequestAttribute JSONObject  reviewer //审稿人，要换成对应的ID
    ){
        String respsonDue = reviewer.getString("responseDue");
        String reviewDue = reviewer.getString("reviewDue");
        String name = reviewer.getString("name");
        String email = reviewer.getString("email");

        long reviewId = reviewerService.findByPidAndEmail( pid, email ).getId();

        ReviewAction ac = ReviewAction.builder()
                            .articleId(aid)
                            .closed(false)
                            .curstate(ReviewAction.REQUESTED)
                            .editorId(uid)
                            .lastUpdate(System.currentTimeMillis())
                            .responseDue(respsonDue)
                            .reviewDue(reviewDue)
                            .reviewId(reviewId)
                            .reviewerName(name)
                            .reviewerEmail(email)
                            .roundId(rid)
                            .fileJson(files)
                            .build();
        ac = rService.save(ac);
        return ac.getId();
    }   

    @RequestMapping("/article/review/queryReviewer")
    public String queryReviewer(
        @RequestAttribute String name,
        @RequestAttribute String email,
        @RequestAttribute String affiliation,
        @RequestAttribute String research,
        @RequestAttribute long pid,
        @RequestAttribute int  page,
        @RequestAttribute int size
    ){
        /**
         * email, name, interests, affiliation, pid,pageRequest, 
         */
        JSONObject obj = reviewerService.queryReviewerLikeUnion(email, name, research, affiliation, pid, page-1,size );
        return JSONObject.toJSONString(obj);
    }

}
