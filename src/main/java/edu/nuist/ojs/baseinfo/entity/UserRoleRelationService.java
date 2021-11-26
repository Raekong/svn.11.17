package edu.nuist.ojs.baseinfo.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class UserRoleRelationService {
    @Autowired
    UserRoleRelationReps userRoleRelationReps;
    public UserRoleRelation save(UserRoleRelation userRoleRelation){
        return userRoleRelationReps.save(userRoleRelation);
    }
    public List<UserRoleRelation> findByUserId(long userId){
        return userRoleRelationReps.findByUserId(userId);
    }
    public List<UserRoleRelation> findByJournalId(long journalId){
        return userRoleRelationReps.findByJournalId(journalId);
    }
    public List<UserRoleRelation> findByRoleId(long roleId){
        return userRoleRelationReps.findByRoleId(roleId);
    }

    public List<Object[]> queryTeam(long jid){
        return userRoleRelationReps.queryTeam(jid);
    }

    public void removeMember(long uurid){
        userRoleRelationReps.removeMember(uurid);
    }

    public UserRoleRelation findByJidAndUidAndRid(long jid, long uid, long rid){
        return userRoleRelationReps.findByJidAndUidAndRid( jid,  uid,  rid);
    }

    public boolean isOffice(long uid, long aid){
        List<UserRoleRelation> rst = userRoleRelationReps.isOffice(uid,aid);
        return !(rst == null || rst.size() == 0);
    }


    public List<UserRoleRelation> getByUidAndPid(long uid, long pid){
        return userRoleRelationReps.getByUidAndPid(uid, pid);
    }   

}
