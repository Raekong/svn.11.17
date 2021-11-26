package edu.nuist.ojs.baseinfo.entity.review;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewActionService {
    @Autowired
    private ReviewActionReps reps;

    public ReviewAction save(ReviewAction r){
        return reps.save(r);
    }

    public List<ReviewAction> findByArticleIdAndRoundId(long ArticleId, long roundId ){
        return reps.findByArticleIdAndRoundId( ArticleId, roundId );
    }  

    public List<ReviewAction> getOverDueActions(String date){
        return reps.getOverDueActions(date);
    }  


    public ReviewAction findById(long id){
        return reps.findById(id);
    }

    public void update(long raid, int status, long lastUpdate){
        reps.update(raid, status, lastUpdate);
    }

    public void complete(long raid, int status,  int type, long lastUpdate){
        reps.complete( raid,  status,   type,  lastUpdate);
    }

    @Transactional
    public void updateOverDue(String date, long last ){
        reps.updateResponseDue(date, last);
        reps.updateReviewDue(date, last);
    }

    public void close(long raid){
        reps.close(raid);
    }

    public void unClose(long raid){
        reps.unClose(raid);
    }

    public void withdrawComplete(long raid){
        reps.withdrawComplete(raid);
    }

    public List<ReviewAction> completeByAidAndRid(long aid, long rid){
        return reps.completeByAidAndRid(aid, rid);
    }

    public List<Object> reviewerStats( String reviewEmail ){
        return reps.reviewerStats(reviewEmail);
    }

    public List<ReviewAction> unclosedActionByAidAndRid(long aid, long rid){
        return reps.unclosedActionByAidAndRid(aid, rid);
    }
}
