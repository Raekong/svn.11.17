package edu.nuist.ojs.baseinfo.monitorentity;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
@Repository
public interface Info_Review_Reps  extends JpaRepository<ReviewInfo, Long> {
    public ReviewInfo save( ReviewInfo ri ); 
    public ReviewInfo findByAid(long aid);
    @Transactional  
	@Modifying
	@Query(value = "update review_info set status=?2 where aid=?1 ", nativeQuery = true)
    public void updateStatus(long aid, long sindex);

    @Query(value = "select * from review_info  where aid in (?1) ", nativeQuery = true)
    public List<ReviewInfo> findByAIds(List<Long> aids);
}
