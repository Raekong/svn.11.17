package edu.nuist.ojs.baseinfo.entity.article;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import edu.nuist.ojs.baseinfo.entity.UserRoleRelationService;
import edu.nuist.ojs.baseinfo.entity.UserService;

@Service
public class ArticleService {
    @Autowired
    private ArticleFileReps afReps;

    @Autowired
    private ArticleAuthorReps aaReps;

    @Autowired
    private ArticleReviewerReps arReps;

    @Autowired
    private ArticleReps aReps;

    @Autowired
    private ArticleHistoryReps hReps;

    @Autowired
    private ArticleEditorBoardReps aceReps;
    
    @Autowired
    private ArticleUserReps auReps;

    public Page<Article> listByAuthor(Pageable pageRequest, long uid ){
        Page<Article> articles = aReps.listByAuthor(pageRequest, uid); 
        articles.forEach(a->{
            Article aa = findById(a.getId());
            a.setAuthors(aa.getAuthors());
            a.setReviewers(aa.getReviewers());
            //a.setFiles(aa.getFiles());
        });
        return articles;
    }

    public Page<ArticleUser> listByEditor(Pageable pageRequest, long uid ){
        Page<ArticleUser> articles = auReps.findByUid(pageRequest, uid);
        return articles;
    }

    
    public Page<Article> listByContributor(Pageable pageRequest, long uid ){
        Page<Article> articles = aReps.listByContributor(pageRequest, uid);
        articles.forEach(a->{
            Article aa = findById(a.getId());
            a.setAuthors(aa.getAuthors());
            a.setReviewers(aa.getReviewers());
           // a.setFiles(aa.getFiles());
        });
        return articles;
    }

    public Article findById( long aid ){
        Article a = aReps.findById(aid);
        // a.setFiles( afReps.findByAid(aid));
        a.setAuthors(aaReps.findByAid(aid));
        a.setReviewers(arReps.findByAid(aid));
        return a;
    }

    public Article save(Article a){
        Article tmp = aReps.save(a);
        for(ArticleFile af: a.getFiles()){
            af.setAid(tmp.getId());
            afReps.save(af);
        }

        for(ArticleReviewer ar: a.getReviewers()){
            ar.setAid(tmp.getId());
            arReps.save(ar);
        }

        for(ArticleAuthor aa: a.getAuthors()){
            aa.setAid(tmp.getId());
            aaReps.save(aa);
        }
        return tmp;

    }

    public void delFile(long fid){
        afReps.delFile(fid);
    }

    public ArticleFile saveFile(ArticleFile af){
        return afReps.save(af);
    }

    public List<ArticleFile> findFilesByAidAndVersion(long aid, String version){
        return afReps.findByAidAndVersion(aid, version);
    }

    public  ArticleFile findFileById(long fid){
        return afReps.findById(fid);
    }



    public List<ArticleFile> findFilesByAidAndVersionAndFileType(long aid, String version, String fileType){
        return afReps.findByAidAndVersionAndFileType(aid, version, fileType);
    }

    public Article isAuthor(long uid, long aid, String email){
        return aReps.isAuthor(uid, aid, email);
    }

    @Transactional
    public void changeJournal(long aid, long jid, long sid ){
        //删除历史
        hReps.forJournalChange(aid);
        //删除FILE
        afReps.delFileForChange( aid);
        //删除EDITOR BOARD
        aceReps.delFileForChange( aid );
        //更新论文期刊与SECTON
        aReps.changeJournal( aid,  jid,  sid );
    }

    public List<ArticleReviewer> findByAid(long aid){
        return arReps.findByAid(aid);
    }

    public List<ArticleFile> findByAidAndVersionAndFileType(long aid, String version, String fileType){
        return afReps.findByAidAndVersionAndFileType(aid, version, fileType);
    }
}
