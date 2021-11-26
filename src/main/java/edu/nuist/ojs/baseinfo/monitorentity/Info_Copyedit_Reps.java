package edu.nuist.ojs.baseinfo.monitorentity;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
@Repository
public interface Info_Copyedit_Reps extends JpaRepository<CopyeditInfo, Long> { 
    public CopyeditInfo save( CopyeditInfo ci ); 
    public CopyeditInfo findByAid(long aid);
    
    @Transactional
	@Modifying
	@Query(value = "update copyedit_info set enddate=?2 where aid=?1 ", nativeQuery = true)
    public void updateEnd(long aid, String date);

    @Query(value = "select * from copyedit_info  where aid in (?1) ", nativeQuery = true)
    public List<CopyeditInfo> findByAIds(List<Long> aids);
}
