package edu.nuist.ojs.baseinfo.monitorentity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShowSettingService {
    @Autowired
    private ShowSettingReps reps;

    public ShowSetting save(ShowSetting ss){
        return reps.save(ss);
    }

    public ShowSetting findByUid(long uid){
        return reps.findByUid(uid);
    }
}
