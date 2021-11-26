package edu.nuist.ojs.baseinfo.monitorentity;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface Info_SimilarRound_Reps  extends JpaRepository<SimilarCheckRound, Long> { 
    public SimilarCheckRound save( SimilarCheckRound scr );  
    public List<SimilarCheckRound> findByAid(long aid);
}
