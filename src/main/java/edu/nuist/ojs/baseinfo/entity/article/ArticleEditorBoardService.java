package edu.nuist.ojs.baseinfo.entity.article;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArticleEditorBoardService {
    @Autowired 
    private ArticleEditorBoardReps reps;

    public ArticleEditorBoard save(ArticleEditorBoard aeb){
        return reps.save(aeb);
    }

    public List<ArticleEditorBoard> findByAid( long aid){
        return reps.findByAid( aid );
    } 

    public List<ArticleEditorBoard> findByAidAndUid( long aid, long uid ){
        return reps.findByAidAndUid(aid, uid);
    }

    public void delEditorByAidAndRidAndEmail(long aid, long rid, long uid ){
        reps.delEditorByAidAndRidAndUid( aid,  rid,  uid);
    }
}
