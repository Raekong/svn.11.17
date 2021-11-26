package edu.nuist.ojs.baseinfo.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import java.util.List;

import javax.transaction.Transactional;

@Repository
public interface UserRoleRelationReps extends JpaRepository<UserRoleRelation,Long> {

    UserRoleRelation save(UserRoleRelation userRoleRelation);

    List<UserRoleRelation> findByUserId(long userId);

    List<UserRoleRelation> findByJournalId(long journalId);

    List<UserRoleRelation> findByRoleId(long roleId);
    //这个方法中GOURP有不兼容的问题，采用https://blog.csdn.net/qq_43647359/article/details/106343013方法解决
    @Query(value = "select r.*, urr.id as urrid, urr.user_id as uid, count(urr.user_id) as num from user_role_relation urr RIGHT JOIN role r on urr.role_id=r.id and (r.journal_id=:jid or r.journal_id=0) and urr.journal_id=:jid group by r.id",  nativeQuery = true)
    List<Object[]> queryTeam(long jid);

    @Transactional
	@Modifying
	@Query(value = "delete from user_role_relation  where id=?1", nativeQuery = true)
    void removeMember(long uurid);

    
    @Query(value = "select * from user_role_relation where journal_id=?1 and role_id=?3 and user_id=?2",nativeQuery = true)
    public UserRoleRelation findByJidAndUidAndRid(long jid, long uid, long rid);


    @Query(value = "select urr.*  from user_role_relation urr, article a where urr.journal_id=a.jid and urr.role_id in (1,2) and urr.user_id=?1 and a.id=?2",nativeQuery = true)
    public List<UserRoleRelation> isOffice(long uid, long aid);

    @Query(value = "select urr.*  from user_role_relation urr, journal j where urr.journal_id=j.journal_id and j.pid =?2 and urr.user_id=?1",nativeQuery = true)
    public List<UserRoleRelation> getByUidAndPid(long uid, long pid);

}
