package edu.nuist.ojs.baseinfo.monitorentity;

import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Service_ArticleInfo {
    @Autowired
    private Info_Article_Reps reps;

    public ArticleInfos save( ArticleInfos ai ){
        return reps.save(ai);
    }

    public ArticleInfos findByAid(long aid){
        return reps.findByAid(aid);
    }

    public void updateStatus(long aid, int sindex)
    {
        reps.updateStatus(aid, sindex);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
        
        reps.lastUpdate(aid, sdf.format(System.currentTimeMillis()));
    }


    public void end(long aid, String date){
        reps.end(aid, date);
    }

    public List<ArticleInfos> findByAIds(List<Long> aids){
        return reps.findByAIds(aids);
    }

}
