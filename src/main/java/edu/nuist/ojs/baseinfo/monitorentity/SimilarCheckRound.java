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
@Table(name = "SimilarCheckRound")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SimilarCheckRound {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long pid;
    private long aid;
    private String result;
    private int total;
    private int first;
    private int second;
    private int third;

    private int seq;
}
