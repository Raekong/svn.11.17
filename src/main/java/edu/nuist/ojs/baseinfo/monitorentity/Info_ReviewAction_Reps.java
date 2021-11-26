package edu.nuist.ojs.baseinfo.monitorentity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface Info_ReviewAction_Reps  extends JpaRepository<ReviewActionInfo, Long> { 
    public ReviewActionInfo save( ReviewActionInfo rai ); 
    public List<ReviewActionInfo> findByAid(long aid);
}
