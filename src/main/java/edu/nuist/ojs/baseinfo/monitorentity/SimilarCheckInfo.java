package edu.nuist.ojs.baseinfo.monitorentity;
import lombok.Data;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import edu.nuist.ojs.baseinfo.entity.SimilarCheck;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "SimilarCheckInfo")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SimilarCheckInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long pid;
    private long aid;
    private long eid;
    private String eemail;
    private String ename;

    private int totalrounds;
    private String startdate;
    private String status;

    @Transient
    private List<SimilarCheck> rounds;

    @Transient
    private String[] checks;
    
    @Transient
    public void getDesc(){
        if( rounds == null) return;
        SimilarCheck last = rounds.get(0);
        for(int i=1; i<rounds.size(); i++){
            if( last.getId()< rounds.get(i).getId()){
                last = rounds.get(i);
            }
        }
        if( last.isPass()){
            this.status = "Pass";
        }else{
            this.status = "Failed";
        }

        this.checks = new String[4];
        this.checks[0] = last.getTotalSimilar();
        this.checks[1] = last.getFrsSimilar();
        this.checks[2] = last.getSecSimilar(); 
        this.checks[3] = last.getThrSimilar();
    }
}
