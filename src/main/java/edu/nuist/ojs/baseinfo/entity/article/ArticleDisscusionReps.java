package edu.nuist.ojs.baseinfo.entity.article;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleDisscusionReps extends JpaRepository<ArticleDisscusion, Long> {
    ArticleDisscusion save(ArticleDisscusion d); 

    @Query(value = "select * from article_disscusion where  aid=?2 and  rid=?1 order by id desc ", nativeQuery = true)
    List<ArticleDisscusion> findByRidAndAid(long rid, long aid);  

    List<ArticleDisscusion> findByRidAndAidAndReceId(long rid, long aid, long receId);  

}
