package edu.nuist.ojs.baseinfo.entity;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {
    @Autowired
    PaymentReps reps;

    public Payment save(Payment p){
        return reps.save(p);
    }

    public List<Payment> getPaymentsByAid(long aid){
        return reps.getPaymentsByAid(aid);
    }

    public Payment getPaymentsById(long payid){
        return reps.getPaymentsById(payid);
    }

    public void audit(long aid){
         reps.audit(aid);
    }
}