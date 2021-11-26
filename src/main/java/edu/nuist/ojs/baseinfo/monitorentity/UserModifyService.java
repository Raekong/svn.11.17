package edu.nuist.ojs.baseinfo.monitorentity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserModifyService {
    @Autowired
    UserModifyReps userModifyReps;
    
    public UserModify findByUserId(long userId){
        return userModifyReps.findByUserId(userId);
    }

    public UserModify save(UserModify userModify){
            return userModifyReps.save(userModify);
    }
}
