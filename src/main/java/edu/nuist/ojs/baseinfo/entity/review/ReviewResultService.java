package edu.nuist.ojs.baseinfo.entity.review;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewResultService {
    @Autowired
    private  ReviewResultReps reps;


    public ReviewResult save(ReviewResult rr){
        return reps.save(rr);
    }
    
    public ReviewResult findById(long id){
        return reps.findById(id);
    }
    
    public ReviewResult findByReviewActionId(long raid){
        return reps.findByReviewActionId(raid);
    }

    public void delByReviewActionId(long raid){
         reps.delByReviewActionId(raid);
    }
    
}
