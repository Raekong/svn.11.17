package edu.nuist.ojs.baseinfo.entity.review;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "ReviewRemindCount")
public class ReviewRemindCount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

    private int total;
    private int manual;
    @Column(name = "sys")
    private int system;
    private int need;
    private int preoid;

    private String timeStamp; //last update
    private long raid;
}
