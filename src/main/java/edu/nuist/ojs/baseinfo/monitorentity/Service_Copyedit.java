package edu.nuist.ojs.baseinfo.monitorentity;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Service_Copyedit {
    @Autowired
    private Info_Copyedit_Reps reps;

    public CopyeditInfo save( CopyeditInfo ci ){
        return reps.save(ci);
    }
    public CopyeditInfo findByAid(long aid){
        return reps.findByAid(aid);
    }

    public void updateEnd(long aid, String date){
        reps.updateEnd(aid, date);
    }

    public List<CopyeditInfo> findByAIds(List<Long> aids){
        return reps.findByAIds(aids);
    }
}
