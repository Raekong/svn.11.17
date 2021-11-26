package edu.nuist.ojs.baseinfo.monitorentity;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Service_Payment {
    @Autowired
    private Info_PaymentHistory_Reps hreps;

    @Autowired
    private Info_Payment_Reps reps;

    
    public PaymentHistory saveHistory( PaymentHistory ph ){
        return hreps.save(ph);
    }

    public List<PaymentHistory> findHistoryByAid(long aid){
        return hreps.findByAid(aid);
    }

    public PaymentInfo save( PaymentInfo pi ){
        return reps.save(pi);
    }

    public PaymentInfo findByAid(long aid){
        return reps.findByAid(aid);
    }
    
    public List<PaymentInfo> findByAIds(List<Long> aids){
        return reps.findByAIds(aids);
    }
}
