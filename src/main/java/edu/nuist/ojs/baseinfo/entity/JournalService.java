package edu.nuist.ojs.baseinfo.entity;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JournalService {

	@Autowired
	private JournalReps jReps;
	
	public Journal save(Journal j){
		 j = jReps.save(j);
		 if( j.getJOrder() == 0.0 ){
			j.setJOrder(j.getJournalId());
			j = jReps.save(j);
		 }
		 
		 return j;
	}

	public List<Journal> findByPid( long pid ){
		return jReps.findByPid(pid);
	}

	public List<Journal> findByAbbreviationAndPid(String abbreviation, long pid){
		return jReps.findByAbbreviationAndPid(abbreviation, pid);
	}

	public void updateOrder(long id,  double order){
		jReps.updateOrder(id, order);
	}

	public Journal findById( long id ){
		return jReps.findById(id);
	}

	public List<Journal> querybyAbbrLike(long pid , String abbreviation){
		return jReps.querybyAbbrLike( pid ,  abbreviation);
	}

}
