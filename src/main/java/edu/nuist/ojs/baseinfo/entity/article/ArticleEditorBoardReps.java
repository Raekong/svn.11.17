package edu.nuist.ojs.baseinfo.entity.article;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleEditorBoardReps extends JpaRepository<ArticleEditorBoard, Long> {
    ArticleEditorBoard save(ArticleEditorBoard ab);

    List<ArticleEditorBoard> findByAid( long aid); 

    List<ArticleEditorBoard> findByAidAndUid( long aid, long uid ); 
    
    @Transactional
	@Modifying
    @Query(value = "delete from article_editor_board where aid=?1 ", nativeQuery = true)
    void delFileForChange(long aid);

     
    @Transactional
	@Modifying
    @Query(value = "delete from article_editor_board where  aid=?1 and  role_id=?2 and uid=?3 ", nativeQuery = true)
    void delEditorByAidAndRidAndUid(long aid, long rid, long uid);
}
