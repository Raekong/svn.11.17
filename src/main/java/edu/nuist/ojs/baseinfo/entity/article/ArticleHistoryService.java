package edu.nuist.ojs.baseinfo.entity.article;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArticleHistoryService {
    @Autowired
    private ArticleHistoryReps ahReps;

    public ArticleHistory findById(long id){
        return ahReps.findById(id);
    }
    
    public ArticleHistory save(ArticleHistory ah){
        return ahReps.save(ah);
    }

    public ArticleHistory getLastStatusById(long aid){
        return ahReps.getLastStatusById(aid);
    }

    public List<Object> getTabs(long aid){ 
        return ahReps.getTabs(aid);
    }

    public ArticleHistory findLastByAidAndStatusAndRound(long aid, String status, int round){
        return ahReps.findLastByAidAndStatusAndRound(aid, status, round);
    }

    public ArticleHistory getNextHistory(long aid, String status, int round){
        return ahReps.getNextHistory(aid, status, round);
    }

    public ArticleHistory findByAidAndStatusAndRound(long aid, String status, int round){
        return ahReps.findByAidAndStatusAndRound( aid,  status,  round);

    }

    public List<ArticleHistory> findByAidAndWorkflow(long aid, String workflow){
        return ahReps.findByAidAndWorkflow(aid, workflow);
    }

    public ArticleHistory getWorkflowHasPreview(long aid){
        return ahReps.getWorkflowHasPreview(aid);
    }

    public ArticleHistory lastHistoryInRound(long aid, int rid){
        return ahReps.lastHistoryInRound(aid,  rid);
    }

    public void forJournalChange(long aid){
        ahReps.forJournalChange( aid);
    }

    public List<ArticleHistory> findByAId(long aid){
        return ahReps.findByAId(aid);
    }

    public void deleteById(long id){
        ahReps.deleteById(id);
    }
}
