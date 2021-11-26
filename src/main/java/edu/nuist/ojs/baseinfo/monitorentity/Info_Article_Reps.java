package edu.nuist.ojs.baseinfo.monitorentity;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
@Repository
public interface Info_Article_Reps  extends JpaRepository<ArticleInfos, Long> {
    public ArticleInfos save( ArticleInfos ai );

    public ArticleInfos findByAid(long aid);

    @Transactional
	@Modifying
	@Query(value = "update article_infos set sindex=?2 where aid=?1 ", nativeQuery = true)
    public void updateStatus(long aid, long sindex);


    @Transactional
	@Modifying
	@Query(value = "update article_infos set lastupdate=?2 where aid=?1 ", nativeQuery = true)
    public void lastUpdate(long aid, String date);

    @Transactional
	@Modifying
	@Query(value = "update article_infos set enddate=?2 where aid=?1 ", nativeQuery = true)
    public void end(long aid, String date);

    @Query(value = "select * from article_infos where aid in (?1) ", nativeQuery = true)
    public List<ArticleInfos> findByAIds(List<Long> aids);
}
