package edu.nuist.ojs.baseinfo.entity.review;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewRemindCountService {
    @Autowired
    private ReviewRemindCountReps reps;

    public ReviewRemindCount save(ReviewRemindCount rrc){
        return reps.save(rrc);
    }

    public ReviewRemindCount findByRaid(long raid){
        return reps.findByRaid(raid);
    }

  
    public void update(long raid, String last, boolean isSystem){
        if(isSystem){
            reps.updateSystem(raid, last);
        }else{
            reps.updateManual(raid, last);
        }
    }

    public List<ReviewRemindCount> getNeedRemindActions(){ 
        return reps.getNeedRemindActions();
    }

}
