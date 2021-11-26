package edu.nuist.ojs.baseinfo.monitorentity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
@Repository
public interface Info_Payment_Reps  extends JpaRepository<PaymentInfo, Long> { 
    public PaymentInfo save( PaymentInfo pi ); 
    public PaymentInfo findByAid(long aid);

    @Query(value = "select * from payment_info  where aid in (?1) ", nativeQuery = true)
    public List<PaymentInfo> findByAIds(List<Long> aids);
}