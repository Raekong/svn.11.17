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
@Table(name = "ArticleInfos")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArticleInfos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    public static final int submit = 0;
    public static final int review_pre_review = 1; 
    public static final int review_round = 2;
    public static final int review_inreview = 3;
    public static final int review_suggest = 4;
    public static final int review_decision_decline = 5;
    public static final int review_decision_accept = 6;
    public static final int review_decision_revision = 7;
    public static final int eview_decision_changejournal = 8;
    public static final int similarity_check_prereview = 9;
    public static final int similarity_check_prereview_declined =10;
    public static final int similarity_check_round =11;
    public static final int similarity_check_round_failed = 12;
    public static final int similarity_check_round_revision = 13;	
    public static final int similarity_check_round_passed = 14;
    public static final int similarity_check_round_declined = 15;
    public static final int similarity_check_round_revisionupload  =16;	
    public static final int payment_waiting = 17;
    public static final int  payment_paid =18;
    public static final int payment_audit_pass = 19;
    public static final int copyedit_waiting_copyedit = 20;
    public static final int copyedit_waiting_revision= 21;
    public static final int copyedit_published = 22;
    public static final int change_journal = 23;

    private long pid;
    private long aid;
    private long eid;
    private String eemail;
    private String ename;
    @Column (columnDefinition="TEXT") 
    private String title;

    private String subdate;
    private String enddate;
    private long subid;
    private String subemail;
    private String subname;
    @Column (columnDefinition="TEXT") 
    private String authors;
    
    private long jid;
    @Column (columnDefinition="TEXT") 
    private String jtitle;
    private long sid;
    @Column (columnDefinition="TEXT") 
    private String stitle;

    private int sindex;
    private String status;

    private String lastupdate;
}
