package edu.nuist.ojs.baseinfo.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JournalSectionReps extends JpaRepository<JournalSection,Long> {

    JournalSection save(JournalSection journalSection);

    JournalSection findById(long id);

    JournalSection findByGuid(String guid);

    @Query(value = "select * from section where journal_id=:journalId order by number, open desc  ",nativeQuery = true)
    List<JournalSection> findByJournalId(long journalId);

    @Query(value = "select * from section where journal_id=?1 and title like concat('%' ,?2,'%'	)  order by title limit 0, 30",nativeQuery = true)
    List<JournalSection> getSectionByTitleLike(long journalId ,String title);

    @Query(value = "select s.* from section s, article a where a.section_id=s.id and a.id=?1",nativeQuery = true)
    JournalSection getSectionByAid(long aid);





}
