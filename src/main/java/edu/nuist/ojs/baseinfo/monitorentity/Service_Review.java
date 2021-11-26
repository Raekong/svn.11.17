package edu.nuist.ojs.baseinfo.monitorentity;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Service_Review {
    @Autowired
    private Info_Review_Reps reps;

    @Autowired
    private Info_ReviewRound_Reps rreps;

    @Autowired
    private Info_ReviewAction_Reps rareps;

    public void updateResult(long aid, int rid, String type){
        rreps.updateResult(aid, rid, type);
    }

    public List<ReviewInfo> findByAIds(List<Long> aids){
        List<ReviewInfo> infos = reps.findByAIds(aids);
        List<ReviewRoundInfo> rounds = rreps.findAllById(aids);

        HashMap<Long, List<ReviewRoundInfo>> tmp = new HashMap<>();

        for(ReviewRoundInfo ri : rounds){
            List<ReviewRoundInfo> rris = tmp.get(ri.getAid());
            if( rris == null) rris = new LinkedList<>();
            rris.add(ri);
            tmp.put(ri.getAid(), rris);
        }

        for(ReviewInfo ri : infos){
            ri.setRounds( tmp.get(ri.getAid()));
        }
        return infos;
    }

    public void inc(long id, String type){
        ReviewRoundInfo rr = rreps.findById(id);
        switch(type){
            case "overdue":
                rr.setOverdue(rr.getOverdue()+1);
                break;
            case "completed":
                rr.setCompleted(rr.getCompleted()+1);
                break;
            case "reviewing":
                rr.setReviewing(rr.getReviewing()+1);
                break;
            case "decline":
                rr.setDecline(rr.getDecline()+1);
                break;
        }
        rreps.save(rr);
    }

    public ReviewInfo save( ReviewInfo ri ){
        return reps.save(ri);
    }

    public ReviewInfo findByAid(long aid){
        return reps.findByAid(aid);
    }

    public void updateStatus(long aid, long sindex){
        reps.updateStatus(aid, sindex);
    }

    public ReviewRoundInfo saveRound( ReviewRoundInfo rri ){
        return rreps.save(rri);
    }

    public ReviewRoundInfo findByAidAndSeq(long aid, int seq){
        return rreps.findByAidAndSeq(aid, seq);
    }

    public List<ReviewRoundInfo>  findRoundsByAid(long aid){
        return rreps.findByAid(aid);
    }

    

    public ReviewActionInfo saveAction( ReviewActionInfo rai ){
        return rareps.save(rai);
    }

    public List<ReviewActionInfo> findActionByAid(long aid){
        return rareps.findByAid(aid);
    }

    
}
