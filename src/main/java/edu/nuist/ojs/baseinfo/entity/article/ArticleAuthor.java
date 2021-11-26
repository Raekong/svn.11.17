package edu.nuist.ojs.baseinfo.entity.article;
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


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "ArticleAuthor")
public class ArticleAuthor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column (name = "indice", columnDefinition="TEXT") //定义成文本
    private int order;
    private long aid;
    private String name;
    private String email;
    private String affiliation;
    private String country;
    private boolean corresponding;
}
