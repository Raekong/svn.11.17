package edu.nuist.ojs.baseinfo.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleReps extends JpaRepository<Role,Long> {

    Role save(Role role);//保存一行

    Role findById(long id);

    Role findByAbbr(String abbr);

    List<Role> findByJournalId(long journalId);

    @Query(value = "select * from role  where journal_id=0 order by id ",nativeQuery = true)
    List<Role> findOriginRole();

    @Query(value = "select * from role where journal_id=0 or journal_id=:jid   order by id ",nativeQuery = true)
    List<Role> findAllRoleForJournal(long jid);

    @Query(value = "select role.* from role, publihser p, journal j where journal_id=0 or ( journal_id=:j.journal_id and p.id=j.pid and p.id=:pid ) order by role.id ",nativeQuery = true)
    List<Role> findAllRoleForPublic(long pid);


}
