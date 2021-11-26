package edu.nuist.ojs.baseinfo.entity.article;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArticleDisscusionService {
    @Autowired
    private ArticleDisscusionReps reps;

    public ArticleDisscusion save(ArticleDisscusion d){
        return reps.save( d);
    }

    public List<ArticleDisscusion> findByRidAndAid(long rid, long aid){
        return reps.findByRidAndAid( rid,  aid);
    }

}
