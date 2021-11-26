package edu.nuist.ojs.baseinfo.entity.article;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "ArticleUser")

@Data
public class ArticleUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long aid;
    private long uid;

}
