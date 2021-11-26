package edu.nuist.ojs.baseinfo.entity.article;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "ArticleReviewer")
public class ArticleReviewer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long aid;
    private String email;
    private String name;
    private String affiliation;
    private String researchfield;
} 
