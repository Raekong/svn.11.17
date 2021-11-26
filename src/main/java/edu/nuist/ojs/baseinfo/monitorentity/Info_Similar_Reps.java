package edu.nuist.ojs.baseinfo.monitorentity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
@Repository
public interface Info_Similar_Reps  extends JpaRepository<SimilarCheckInfo, Long> { 
    public SimilarCheckInfo save( SimilarCheckInfo si ); 
    public SimilarCheckInfo findByAid(long aid);

    @Query(value = "select * from  similar_check_info  where aid in (?1) ", nativeQuery = true)
    public List<SimilarCheckInfo> findByAIds(List<Long> aids);
}
