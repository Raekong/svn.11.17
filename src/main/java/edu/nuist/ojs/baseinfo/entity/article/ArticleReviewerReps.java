package edu.nuist.ojs.baseinfo.entity.article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public interface ArticleReviewerReps extends JpaRepository<ArticleReviewer, Long> {
    ArticleReviewer save(ArticleReviewer ar);

    List<ArticleReviewer> findByAid(long aid);
}
