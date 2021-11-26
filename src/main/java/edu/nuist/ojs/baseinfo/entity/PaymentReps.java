package edu.nuist.ojs.baseinfo.entity;



import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentReps extends JpaRepository<Payment,Long> {
    Payment save(Payment p);

    @Query(value = "select * from pay_info where aid=?1 order by create_time", nativeQuery = true)
    List<Payment> getPaymentsByAid(long aid); 

    Payment getPaymentsById(long payid);

    @Transactional
	@Modifying
	@Query(value = "update pay_info set state=2 where aid=?1 ", nativeQuery = true)
    void audit(long aid);

}
