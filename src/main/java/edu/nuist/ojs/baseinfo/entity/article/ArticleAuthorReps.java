package edu.nuist.ojs.baseinfo.entity.article;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleAuthorReps extends JpaRepository<ArticleAuthor, Long> {
    ArticleAuthor save(ArticleAuthor aa);

    List<ArticleAuthor> findByAid(long aid);
    
}
