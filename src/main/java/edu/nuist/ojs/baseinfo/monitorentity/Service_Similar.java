package edu.nuist.ojs.baseinfo.monitorentity;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Service_Similar {
    @Autowired
    private Info_Similar_Reps reps;

    @Autowired
    private Info_SimilarRound_Reps rreps;

    public SimilarCheckInfo save( SimilarCheckInfo si ){
        return reps.save(si);
    }
    public SimilarCheckInfo findByAid(long aid){
        return reps.findByAid(aid);
    }

    public SimilarCheckRound saveRound( SimilarCheckRound scr ){
        return rreps.save(scr);
    } 
    public List<SimilarCheckRound> findRoundByAid(long aid){
        return rreps.findByAid(aid);
    }
    
    public List<SimilarCheckInfo> findByAIds(List<Long> aids){
        return reps.findByAIds(aids);
    }
}
