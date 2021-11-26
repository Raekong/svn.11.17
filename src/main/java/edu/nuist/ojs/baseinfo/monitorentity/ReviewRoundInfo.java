package edu.nuist.ojs.baseinfo.monitorentity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ReviewRoundInfo")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewRoundInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    public static final String DECLINE = "decline";
    public static final String ACCEPT = "accept";
    public static final String REVISION = "revision";

    private long pid;
    private long aid;
    private int seq;
    @Column (columnDefinition="TEXT") 
    private String reviewers;

    private int total;
    private int completed;
    private int reviewing;
    private int overdue;
    private int decline;
    private String result;
}
