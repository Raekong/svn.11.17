package edu.nuist.ojs.baseinfo.entity.article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

import javax.transaction.Transactional;
@Repository
public interface ArticleFileReps extends JpaRepository<ArticleFile, Long> {
    ArticleFile save(ArticleFile af);

    List<ArticleFile> findByAidAndVersion(long aid, String version);

    List<ArticleFile> findByAidAndVersionAndFileType(long aid, String version, String fileType);

    List<ArticleFile> findByAid(long aid);

    ArticleFile findById(long id);

    @Transactional
	@Modifying
	@Query(value = "delete from article_file where id=?1 ", nativeQuery = true)
    void delFile(long fid);

    @Transactional
	@Modifying
    @Query(value = "delete from article_file where aid=?1 and version!='SUBMIT-0' ", nativeQuery = true)
    void delFileForChange(long aid);


}
