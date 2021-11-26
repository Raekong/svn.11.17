package edu.nuist.ojs.baseinfo.entity;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SimilarCheckReps extends JpaRepository<SimilarCheck,Long> {
    SimilarCheck save(SimilarCheck similarCheck);

    @Query(value = "select * from similarcheck where aid=?1  order by round DESC ",nativeQuery = true)
    List<SimilarCheck> findByAid(long aid);

    SimilarCheck findByAidAndRound(long aid,int round);
    
    @Query(value = "select * from similarcheck where total_similar is null and checkid is not null ",nativeQuery = true)
    List<SimilarCheck> findUnchecked();

    @Query(value = "select * from similarcheck where uploaded=false",nativeQuery = true)
    List<SimilarCheck> findUnuploaded();


}
