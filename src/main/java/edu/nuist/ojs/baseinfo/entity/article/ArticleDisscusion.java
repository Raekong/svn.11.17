package edu.nuist.ojs.baseinfo.entity.article;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "ArticleDisscusion")
@Data
public class ArticleDisscusion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long aid;
    private long rid;
    private long sendId;
    private long receId;
    private String sendEmail;
    private long msgId;
    private int type;
}
