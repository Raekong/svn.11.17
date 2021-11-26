package edu.nuist.ojs.baseinfo.monitorentity;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
@Repository
public interface Info_ReviewRound_Reps extends JpaRepository<ReviewRoundInfo, Long> { 
    public ReviewRoundInfo save( ReviewRoundInfo rri ); 
    public List<ReviewRoundInfo>  findByAid(long aid);
    
    public ReviewRoundInfo findByAidAndSeq(long aid, int seq);

    
    public ReviewRoundInfo findById(long id);

    @Transactional
	@Modifying
	@Query(value = "update review_round_info set result=?3 where aid=?1 and seq=?2 ", nativeQuery = true)
    public void updateResult(long aid, int rid, String  result);

    @Query(value = "select * from review_round_info  where aid in (?1) order by aid", nativeQuery = true)
    public List<ReviewRoundInfo> findByAIds(List<Long> aids);
}
