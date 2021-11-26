package edu.nuist.ojs.baseinfo.entity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface JournalSettingReps extends JpaRepository<JournalSetting,Long> {

    JournalSetting save(JournalSetting journalSetting);

    List<JournalSetting> findByJournalId(long journalId);

    JournalSetting findById(long id);
    
    JournalSetting findByJournalIdAndConfigPoint(long journalId,String configPoint);

	@Query(value = "select * from journal_setting js, journal jb where jb.pid=:pid and js.config_point=:configPoint ",nativeQuery = true)
    List<JournalSetting> findByPIdAndConfigPoint(long pid, String configPoint);

    

}
