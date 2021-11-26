package edu.nuist.ojs.baseinfo.entity;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
public interface UserReps extends JpaRepository<User, Long> {

	public User findByUserId(long userId);

	public User findByPublisherIdAndEmail(long publisherId, String email);

    public User findByPublisherIdAndEmailAndPassword(long publisherId, String email, String password);

    public List<User> findByPublisherId(long publisherId);

	@Query(value = "select * from user where publisher_id=?1 and email like concat('%' ,?2,'%'	)  order by email limit 0, 20",nativeQuery = true)
	public List<User> findByPublisherIdAndEmailLike(long publisherId, String email);

	@Query(value = "select * from user where publisher_id=?1 and username like concat('%' ,?2,'%'	)  order by username	 limit 0, 20",nativeQuery = true)
	public List<User> findByPublisherIdAndNameLike(long publisherId, String name);

    public List<User> findByRoot(boolean root);

	@Query(value = "select * from user where email=:email and super_user=true",nativeQuery = true)
	public User findSuperByEmail(String email);


	public User save(User user);

	@Transactional
	@Modifying
	@Query(value = "update user set is_actived=true where user_id=?1 ", nativeQuery = true)
	public void setActive(long id);

	@Transactional
	@Modifying
	@Query(value = "update user set password=?2 where user_id=?1 ", nativeQuery = true)
	public void resetPassword(long id, String password);

	@Transactional
	@Modifying
	@Query(value = "update user set i18n=?2 where user_id=?1 ", nativeQuery = true)
	public void updateI18n(long id, String lang);


	@Transactional
	@Modifying
	@Query(value = "update user set password=?3 where publisher_id=?1 and email=?2 ", nativeQuery = true)
	public void updatePassword(long publishId, String email, String password);

	
    @Query(value = "select u.*, urr.id as uurid from user u, user_role_relation urr where urr.role_id=:rid and urr.user_id=u.user_id and urr.journal_id=:jid ", nativeQuery = true)
	public List<Object[]> getTeamMember(long jid, long rid);


	@Query(value = "select * from user where publisher_id=?1 and email like concat('%' ,?2,'%')	and username like concat('%' ,?3,'%' )  order by username ",nativeQuery = true)
	public Page<User> findByNameAndEmailLike(Pageable pq, long pid, String email, String name);

	@Query(value = "select * from user where user.user_id in (select uur.user_id from user_role_relation uur where uur.role_id=?2 ) and publisher_id=?1 and email like concat('%' ,?3,'%' ) and username like concat('%' ,?4,'%' ) order by username", nativeQuery = true)
	public Page<User> findByRoleIdAndNameAndEmailLike(Pageable pq, long pid, long rid, String email, String name);

	@Query(value = "select * from user where user.user_id in (select uur.user_id from user_role_relation uur where uur.journal_id=?1 ) and email like concat('%' ,?2,'%' ) order by username limit 0, 50", nativeQuery = true)
	public List<User> getAllEditorForJournalAndEmailLike(long jid, String email);

	@Query(value = "select * from user u, user_role_relation uur where uur.journal_id=?1 and uur.role_id=1 and uur.user_id=u.user_id", nativeQuery = true)
	public User findManagerForJournal(long jid);

	@Query(value = "select u.* from user u, article a, section s where a.pid=u.publisher_id and a.section_id=s.id and s.section_editor=u.email and a.jid=s.journal_id and a.id=?1", nativeQuery = true)
	public User findUserBySectionId(long aid);

	@Query(value = "select u.* from user u, user_role_relation urr where u.user_id=urr.user_id and urr.journal_id=?1 and (  urr.role_id=?2 or urr.role_id=(select id from role where journal_id=?1 and same_level=?2))", nativeQuery = true)
	public List<User> findUserByRoleForJournal(long jid, long roldid);

	@Query(value = "select u.* from user u, article_editor_board aeb where u.user_id=aeb.uid and aeb.aid=?1", nativeQuery = true)
	public List<User> findEditorBoardByAid(long aid);

	@Query(value = "select u.* from user u, article a where u.email=?2 and u.publisher_id=a.pid and a.id=?1", nativeQuery = true)
	public User findEditorByAidAndEmail(long aid, String email);

	@Query(value = "select * from user where email like concat('%' ,?1,'%' ) and username like concat('%' ,?2,'%' ) and interests like concat('%' ,?3,'%' ) and affiliation like concat('%' ,?4,'%' ) and publisher_id=?5 order by email limit 0, 50", nativeQuery = true)
	public List<User> findByNameAndEmailAndInterestsAndAffiliationLike(String email, String name, String interests, String affiliation, long pid);

}
