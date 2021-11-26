package edu.nuist.ojs.baseinfo.monitorentity;

import lombok.Data;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ReviewActionInfo")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewActionInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long pid;
    private long aid;
    private long rid;
    private String remail;
    private String rname;

    private String result;
    private String startdate;
    private String enddate;

    private int remindnum;
}
