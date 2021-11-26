package edu.nuist.ojs.baseinfo.monitorentity;

import lombok.Data;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ReviewInfo")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewInfo {
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
    private int status;
    
    @Transient
    private List<ReviewRoundInfo> rounds;

    @Transient
    private ReviewRoundInfo lastRound;

    public ReviewRoundInfo getLastRound(){
        if( rounds == null) return null;
        ReviewRoundInfo last = rounds.get(0);
        for(int i=1; i<rounds.size(); i++){
            if( last.getId()< rounds.get(i).getId()){
                last = rounds.get(i);
            }
        }
        this.lastRound = last;
        return last;
    }
}
