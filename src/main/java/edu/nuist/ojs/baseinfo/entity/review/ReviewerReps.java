package edu.nuist.ojs.baseinfo.entity.review;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewerReps  extends JpaRepository<Reviewer, Long> {
    Reviewer save(Reviewer r); 

    public Reviewer findByPidAndEmail(long pid, String email);

    @Query(value = "select * from reviewer where email like concat('%' ,?1,'%' ) and name like concat('%' ,?2,'%' ) and research_field like concat('%' ,?3,'%' ) and affiliation like concat('%' ,?4,'%' ) order by email  limit 0, 50", nativeQuery = true)
	List<Reviewer> findByNameAndEmailAndInterestsAndAffiliationLike(String email, String name, String interests, String affiliation, long pid);

    @Query(value = "select * from reviewer where id in ( select review_id from review_action where  round_id=?2 and article_id=?1) ", nativeQuery = true)
	List<Reviewer> findByAidAndRoundId( long aid, long rid);

    @Query(value = "SELECT t.email, t.name, t.research, t.affiliation, max(t.total_review) as total, max(t.completed_review) as completed  FROM ("
                  +" select r.email, r.name, r.research_field as research, r.affiliation,  r.total_review, r.completed_review   from reviewer r where email like concat('%' ,?1,'%' ) and name like concat('%' ,?2,'%' ) and research_field like concat('%' ,?3,'%' ) and affiliation like concat('%' ,?4,'%' ) and pid=?5"
                  +" union"
                  +" select u.email as email , u.username as name , u.interests as research, u.affiliation  as affiliation, null as total_review, null as completed_review  from user u where email like concat('%' ,?1,'%' ) and username like concat('%' ,?2,'%' ) and interests like concat('%' ,?3,'%' ) and affiliation like concat('%' ,?4,'%' ) and publisher_id=?5"
                  +" )  as t group by email order by total desc limit ?6, ?7",
                  nativeQuery = true)
    List<Object[]> queryReviewerLikeUnion(String email, String name, String interests, String affiliation, long pid, int start, int num);


    @Query(value = "Select count(*) as total from (SELECT t.email, t.name, t.research, t.affiliation, max(t.total_review) as total, max(t.completed_review) as completed  FROM ("
                  +" select r.email, r.name, r.research_field as research, r.affiliation,  r.total_review, r.completed_review   from reviewer r where email like concat('%' ,?1,'%' ) and name like concat('%' ,?2,'%' ) and research_field like concat('%' ,?3,'%' ) and affiliation like concat('%' ,?4,'%' ) and pid=?5"
                  +" union"
                  +" select u.email as email , u.username as name , u.interests as research, u.affiliation  as affiliation, null as total_review, null as completed_review  from user u where email like concat('%' ,?1,'%' ) and username like concat('%' ,?2,'%' ) and interests like concat('%' ,?3,'%' ) and affiliation like concat('%' ,?4,'%' ) and publisher_id=?5"
                  +" )  as t group by email) as k",
                  nativeQuery = true)
    List<Object[]> queryReviewerLikeUnionCount(String email, String name, String interests, String affiliation, long pid);

}
