package edu.nuist.ojs.baseinfo.monitorentity;
import lombok.Data;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import edu.nuist.ojs.baseinfo.entity.Payment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "PaymentInfo")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    

    private long pid;
    private long aid;
    private long eid;
    private String eemail;
    private String ename;

    private int apc;
    private int totalpage;
    private String startdate;
    private int totalpaid;
    @Transient
    private List<Payment> histories ;
    public void getDesc(){
        int total = 0;
        for(Payment p : this.histories){
            if( p.getOrgPageNumber() != 0){
                this.totalpage = p.getOrgPageNumber();
            }

            if( p.getOrgTotalAPC()!= 0){
                this.apc = p.getOrgTotalAPC();
            }

            total += p.getPayTotal();
        } 
        this.totalpaid = total;
    }

}
