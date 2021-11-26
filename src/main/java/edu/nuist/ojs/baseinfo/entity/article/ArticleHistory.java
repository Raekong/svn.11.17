package edu.nuist.ojs.baseinfo.entity.article;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "ArticleHistory")
public class ArticleHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long aid;
    private String workflow;
    private String status;
    private int round;

    @Column (name = "desctxt", columnDefinition="TEXT") //定义成文本
    private String desc;
    private long userId;
    private String username;
    private String userEmail;
    private long msgId;    

    public String getFileVersion(){
        return this.getWorkflow() + "-" + this.getRound();
    }

    @Column(name = "create_time", insertable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp stamp;
}

