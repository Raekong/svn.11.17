package edu.nuist.ojs.baseinfo.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SimilarCheckService {
    @Autowired
    SimilarCheckReps similarCheckReps;
    public SimilarCheck save(SimilarCheck similarCheck){
        return similarCheckReps.save(similarCheck);
    }

    public List<SimilarCheck> findByAid(long aid){
        return similarCheckReps.findByAid(aid);
    }
    public SimilarCheck findByAidAndRound(long aid,int round){
        return similarCheckReps.findByAidAndRound(aid,round);
    }
    public List<SimilarCheck> findUnchecked(){
        return similarCheckReps.findUnchecked();
    }

    public List<SimilarCheck> findUnuploaded(){
        return similarCheckReps.findUnuploaded();
    }
}
