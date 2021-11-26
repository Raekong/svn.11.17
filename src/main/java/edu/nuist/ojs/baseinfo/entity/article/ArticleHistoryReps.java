package edu.nuist.ojs.baseinfo.entity.article;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleHistoryReps extends JpaRepository<ArticleHistory, String> {
    public ArticleHistory save(ArticleHistory ah);
    
    @Query(value = "select ah.* from article_history ah where ah.aid=?1 order by id desc limit 0, 1", nativeQuery = true)
    public ArticleHistory getLastStatusById(long aid);
   
    @Query(value = "select distinct(workflow) from article_history ah where ah.aid=?1 order by id desc", nativeQuery = true)
    public List<Object> getTabs(long aid); 

    public ArticleHistory findById(long id);

    @Query(value = "select * from article_history ah where ah.aid=?1 order by id desc", nativeQuery = true)
    public List<ArticleHistory> findByAId(long aid);

    public ArticleHistory findByAidAndStatusAndRound(long aid, String status, int round);

    @Query(value = "select * from article_history ah where ah.aid=?1 and ah.id<(select id from article_history where aid=?1 and status=?2 and round=?3) order by id desc limit 0, 1", nativeQuery = true)
    public ArticleHistory findLastByAidAndStatusAndRound(long aid, String status, int round);

    @Query(value = "select * from article_history ah where ah.aid=?1 and ah.id>(select id from article_history where aid=?1 and status=?2 and round=?3) order by id limit 0, 1", nativeQuery = true)
    public ArticleHistory getNextHistory(long aid, String status, int round);

    @Query(value = "select * from article_history ah where ah.aid=?1 and workflow=?2 order by id desc", nativeQuery = true)
    public List<ArticleHistory> findByAidAndWorkflow(long aid, String workflow); 

    @Query(value = "select * from article_history ah where ah.aid=?1 and status like '%pre%' order by id desc limit 0, 1", nativeQuery = true)
    public ArticleHistory getWorkflowHasPreview(long aid); 

    @Query(value = "select * from article_history ah where ah.aid=?1 and ah.round=?2 order by id desc limit 0, 1", nativeQuery = true)
    public ArticleHistory lastHistoryInRound(long aid, int round); 

    @Transactional
	@Modifying
    @Query(value = "delete from article_history where aid=?1 and workflow != 'SUBMIT' ", nativeQuery = true)
    public void forJournalChange(long aid);


    @Transactional
	@Modifying
    @Query(value = "delete from article_history where id=?1 ", nativeQuery = true)
    public void deleteById(long  id);
}
