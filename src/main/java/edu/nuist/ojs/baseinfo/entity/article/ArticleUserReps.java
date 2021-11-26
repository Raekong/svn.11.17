package edu.nuist.ojs.baseinfo.entity.article;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**create view sql:
 * create view article_user as  select t.id as aid, t.uid as uid from ((select a.id, aeb.uid from article_editor_board aeb, article a where aeb.aid=a.id) union DISTINCT (select a.id, urr.user_id as uid from article a, user_role_relation urr where a.jid=urr.journal_id and urr.role_id in (1,2) )) as t
 */

@Repository
public interface ArticleUserReps extends JpaRepository<ArticleUser, Long> {
    List<ArticleUser> findByUid(long uid);
    
    Page<ArticleUser> findByUid(Pageable pageRequest, long uid );
}
