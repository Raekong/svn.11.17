package edu.nuist.ojs.baseinfo.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {
    @Autowired
    RoleReps roleReps;
    public Role save(Role role){
        return roleReps.save(role);
    }
    public Role findById(long id){
        return roleReps.findById(id);
    }
    public List<Role> findByJournalId(long journalId){
        return roleReps.findByJournalId(journalId);
    }

    public  Role findByAbbr(String abbr){
        return roleReps.findByAbbr(abbr);
    }

    
	public List<Role> findOriginRole(){
		return roleReps.findOriginRole();
	}

    public List<Role> findAllRoleForJournal(long jid){//no matter origin role or personal role
        return roleReps.findAllRoleForJournal(jid);
    }

    public List<Role> findAllRoleForPublic(long pid){
        return roleReps.findAllRoleForJournal(pid);
    }


}
