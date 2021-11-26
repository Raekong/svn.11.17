package edu.nuist.ojs.baseinfo.controller;

import com.alibaba.fastjson.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.nuist.ojs.baseinfo.entity.Payment;
import edu.nuist.ojs.baseinfo.entity.PaymentService;

@RestController
public class PaymentController {

    @Autowired
    private PaymentService pService;

    @RequestMapping("/payment/save")
    public Payment savePayment(@RequestAttribute Payment payment){
        return pService.save(payment);
    }
    

    @RequestMapping("/payment/getPaymentsByAid")
    public String getPaymentsByAid(@RequestAttribute long aid){
        return JSONObject.toJSONString( pService.getPaymentsByAid(aid));
    }

    @RequestMapping("/payment/getPaymentById")
    public Payment getPaymentsById(@RequestAttribute long payid){
        return  pService.getPaymentsById(payid);
    }


    @RequestMapping("/payment/audit")
    public String payAudit(@RequestAttribute long aid){
        pService.audit(aid);
        return "";
    }

}
