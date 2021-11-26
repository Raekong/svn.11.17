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

import org.springframework.data.annotation.CreatedDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "Article")
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long pid;
    private long jid;
    private long sectionId;
    private long submitorId;
    private String title;

    @Column(name="create_time",columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false,updatable = false)
	@CreatedDate
	private Timestamp timeStamp;

    @Column (columnDefinition="TEXT") //定义成文本
    private String abstractTxt;
    @Column (columnDefinition="TEXT") //定义成文本
    private String keywords;

    @Transient
    private List<ArticleAuthor> authors;

    @Transient
    private List<ArticleFile> files;

    @Transient
    private List<ArticleReviewer> reviewers;
    
}
