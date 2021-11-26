package edu.nuist.ojs.baseinfo.monitorentity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface Info_PaymentHistory_Reps extends JpaRepository<PaymentHistory, Long> { 
    public PaymentHistory save( PaymentHistory ai );
    public List<PaymentHistory> findByAid(long aid);
    
}
