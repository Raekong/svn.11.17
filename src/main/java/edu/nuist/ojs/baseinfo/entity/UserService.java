package edu.nuist.ojs.baseinfo.entity;


import cn.hutool.crypto.SecureUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;


/**
 * @author MicJoy
 *
 * <p> 业务逻辑的实现
 *
 */
@Service
@Transactional
public class UserService {

	@Autowired
	private edu.nuist.ojs.baseinfo.entity.UserReps UserReps;

	public void resetPassword(String password, long user_id) {
		String md5password = SecureUtil.md5(password);
		UserReps.resetPassword(user_id, md5password);
	}

	public User save(User user) {
		String fname = user.getFirstname();
		fname = (fname==null ? "":fname + " "); 

		String mname = user.getMiddlename();
		mname = (mname==null ? "": mname+ " "); 

		String lname = user.getLastname();
		lname = (lname==null ? "": lname+ " "); 

		user.setUsername( fname + mname + lname );
		return UserReps.save(user);
	}

	public User update(User user){
		return save(user);
	}

	public void setActive(long id){
		UserReps.setActive(id);
	}

	public List<User> findByPublisherIdAndEmailLike(long publisherId, String email){
		return UserReps.findByPublisherIdAndEmailLike(publisherId,  email);
	}

	public List<User> findByPublisherIdAndNameLike(long publisherId, String name){
		return UserReps.findByPublisherIdAndNameLike(publisherId,  name);
	}


	public User findByUserId(long userId){return UserReps.findByUserId(userId);}

	public User findByPublisherIdAndEmail(long publisherId, String email){
		return UserReps.findByPublisherIdAndEmail(publisherId,email);
	}


	public User findByPublisherIdAndEmailAndPassword(long publisherId, String email, String password){
		String md5password=SecureUtil.md5(password);
		return UserReps.findByPublisherIdAndEmailAndPassword(publisherId,email,md5password);
	}
	
	public List<User> findByPublisherId(long publisherId){
		return UserReps.findByPublisherId(publisherId);
	}
	
	public User findSuperByEmail(String email){
		return UserReps.findSuperByEmail(email);
	}

	public List<User> findByRoot(boolean root){
		return UserReps.findByRoot(root);
	}
	
	public User checkAndSave(User user){

		if(findByPublisherIdAndEmail(user.getPublisherId(),user.getEmail())==null){
			String md5password = SecureUtil.md5(user.getPassword());
			user.setPassword(md5password);
			
			if( user.getUsername() == null || "".equals(user.getUsername())){
				String fname = user.getFirstname();
				fname = (fname==null ? "":fname + " "); 
	
				String mname = user.getMiddlename();
				mname = (mname==null ? "": mname+ " "); 
	
				String lname = user.getLastname();
				lname = (lname==null ? "": lname+ " "); 
	
				user.setUsername( fname + mname + lname );
			}
			return save(user);
		}
		return null;
	}

	public void updateI18n(long id, String lang){
		UserReps.updateI18n(id, lang);
	}

	public User updatePassword(long publisherId, String email, String password){
		String md5password = SecureUtil.md5(password);
		UserReps.updatePassword(publisherId,  email,   md5password);
		return UserReps.findByPublisherIdAndEmail(publisherId, email);
	}

	public List<Object[]>  getTeamMember(long jid, long rid){
		return UserReps.getTeamMember(jid, rid);
	}

	public Page<User> findByNameAndEmailLike(Pageable pq, long pid, String email, String name){
		return UserReps.findByNameAndEmailLike(pq, pid, email,   name);
	}

	public Page<User> findByRoleIdAndNameAndEmailLike(Pageable pq, long pid, long rid, String email, String name){
		return UserReps.findByRoleIdAndNameAndEmailLike(pq, pid, rid, email,   name);
	}

	public List<User> getAllEditorForJournalAndEmailLike(long jid, String email){
		return UserReps.getAllEditorForJournalAndEmailLike( jid,  email);
	}

	public User findManagerForJournal(long jid){
		return UserReps.findManagerForJournal( jid);
	}

	public User findUserBySectionId(long aid){
		return UserReps.findUserBySectionId(aid);
	}

	public List<User> findUserByRoleForJournal(long jid, long roldid){
		return UserReps.findUserByRoleForJournal(jid, roldid);
	}

	public List<User> findEditorBoardByAid(long aid){
		return UserReps.findEditorBoardByAid(aid);
	}
	public User findEditorByAidAndEmail(long aid, String email){
		return UserReps.findEditorByAidAndEmail(aid, email);
	}

	public List<User> findByNameAndEmailAndInterestsAndAffiliationLike(String email, String name, String interests, String affiliation, long pid){
		return UserReps.findByNameAndEmailAndInterestsAndAffiliationLike(email,  name,  interests,  affiliation,  pid);
	}


	public User findByPublisherIdAndEmailAndPassword1(long publisherId, String email, String md5password){
		return UserReps.findByPublisherIdAndEmailAndPassword(publisherId,email,md5password);
	}
}

