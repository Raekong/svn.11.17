package edu.nuist.ojs.baseinfo.entity.review;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewResultReps extends JpaRepository<ReviewResult, Long> {
    ReviewResult save(ReviewResult rr);

    ReviewResult findById(long id);

    ReviewResult findByReviewActionId(long raid);

    @Transactional
	@Modifying
    @Query(value = "delete from  review_result where review_action_id=?1", nativeQuery = true)
    void delByReviewActionId(long raid);
}
