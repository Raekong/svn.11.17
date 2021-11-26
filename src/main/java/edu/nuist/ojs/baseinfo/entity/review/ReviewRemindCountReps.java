package edu.nuist.ojs.baseinfo.entity.review;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

@Repository
public interface ReviewRemindCountReps extends JpaRepository<ReviewRemindCount, Long> {

    ReviewRemindCount save(ReviewRemindCount rrc);

    ReviewRemindCount findByRaid(long raid);

    @Transactional
	@Modifying
    @Query(value = "update review_remind_count set  total=total+1, sys=sys+1 , time_stamp=?2  where raid=?1 ", nativeQuery = true)
    void updateSystem(long raid, String lastupdate);

    @Transactional
	@Modifying
    @Query(value = "update review_remind_count set  total=total+1, manual=manual+1, time_stamp=?2  where raid=?1 ", nativeQuery = true)
    void updateManual(long raid, String lastupdate);


    @Query(value = "select * from review_remind_count where total<need and datediff(curdate() , time_stamp) > preoid ", nativeQuery = true) 
    List<ReviewRemindCount> getNeedRemindActions();  
}
