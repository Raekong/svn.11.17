package edu.nuist.ojs.baseinfo.entity.review;


import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewActionReps extends JpaRepository<ReviewAction, Long> {
    ReviewAction save(ReviewAction r); 

    List<ReviewAction> findByArticleIdAndRoundId(long ArticleId, long roundId );  

    ReviewAction findById(long id); 

    @Transactional
	@Modifying
    @Query(value = "update review_action set curstate=?2, last_update=?3 where id=?1 ", nativeQuery = true)
    void update(long raid, int status, long lastUpdate);

    @Transactional
	@Modifying
    @Query(value = "update review_action set curstate=?2, result_type=?3 ,last_update=?4 where id=?1 ", nativeQuery = true)
    void complete(long raid, int status,  int type, long lastUpdate);

    @Transactional
    //及时更新缓存
	@Modifying(clearAutomatically = true, flushAutomatically = true) 
    @Query(value = "update review_action set curstate=5, last_update=?2 where curstate=0 and ?1>response_due", nativeQuery = true)
    void updateResponseDue(String date, long lastUpdate);

    @Transactional
    //及时更新缓存
	@Modifying(clearAutomatically = true, flushAutomatically = true) 
    @Query(value = "update review_action set curstate=6, last_update=?2 where curstate=2 and ?1>review_due", nativeQuery = true)
    void updateReviewDue(String date, long lastUpdate);


    @Query(value = "select * from review_action where id in ( select distinct(id) from  review_action where (curstate=0 and ?1>response_due) or (curstate=2 and ?1>review_due))  ", nativeQuery = true)
    List<ReviewAction> getOverDueActions(String date);

    @Transactional
	@Modifying
    @Query(value = "update review_action set closed=true where id=?1", nativeQuery = true)
    void close(long raid);

    @Transactional
	@Modifying
    @Query(value = "update review_action set closed=false where id=?1", nativeQuery = true)
    void unClose(long raid);

    @Transactional
	@Modifying
    @Query(value = "update review_action set closed=false, curstate=2, result_type=0 where id=?1", nativeQuery = true)
    void withdrawComplete(long raid);

    @Query(value = "select * from  review_action where article_id=?1 and round_id=?2 and curstate=3", nativeQuery = true)
    List<ReviewAction> completeByAidAndRid(long aid, long rid);

    @Query(value = "select * from  review_action where article_id=?1 and round_id=?2 and curstate!=3 and curstate!=1 and closed=false", nativeQuery = true)
    List<ReviewAction> unclosedActionByAidAndRid(long aid, long rid);

    @Query(value = "select count(*) as total, sum(case when curstate=2 or curstate=0 then 1 else 0 end) as inreview from review_action where reviewer_email=?1", nativeQuery = true)
    List<Object> reviewerStats( String reviewEmail );
}
