package edu.nuist.ojs.baseinfo.entity.article;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleReps extends JpaRepository<Article, Long> {
    Article save( Article a );
    
    
    Article findById(long aid);

    @Query(value = "select * from article where submitor_id=?1 order by id desc", nativeQuery = true)
    Page<Article> listByAuthor(Pageable pageRequest, long uid );  

    
    @Query(value = "select a.* from user u, article a, article_author aa  where aa.email=u.email and a.id=aa.aid and u.publisher_id = a.pid and a.submitor_id!=?1 and u.user_id=?1  order by a.id desc", nativeQuery = true)
    Page<Article> listByContributor(Pageable pageRequest, long uid );

    // @Query(value = 
    //         "select a.* from article a, "
    //        +"((select a.id from article_editor_board aeb, article a where aeb.uid=?1 and aeb.aid=a.id ) "
    //        +"  union DISTINCT  "
    //        +" (select a.id from article a, user_role_relation urr where a.jid=urr.journal_id and urr.user_id=?1 and urr.role_id in (1,2) )"
    //        +"  order by id desc) as t "
    //        +"where a.id=t.id order by a.id desc", nativeQuery = true)
    // Page<Article> listByEditor(Pageable pageRequest, long uid );

    

    @Query(value = "select a.* from article a  where a.submitor_id=?1 and a.id=?2 UNION select a.* from article a, article_author aa  where a.id=aa.aid and a.id=?2 and aa.email=?3", nativeQuery = true)
    public Article isAuthor(long uid, long aid, String email);

    @Transactional
	@Modifying
    @Query(value = "update article set section_id=?3, jid=?2 where id=?1 ", nativeQuery = true)
    public void changeJournal(long aid, long jid, long sid);
    
}
