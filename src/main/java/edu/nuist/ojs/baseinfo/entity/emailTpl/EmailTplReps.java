package edu.nuist.ojs.baseinfo.entity.emailTpl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailTplReps extends JpaRepository<EmailTemplate, Long> {
    EmailTemplate save(EmailTemplate emailTemplate);

    @Query(value = "select tpl.* from email_config_point ecp, email_tpl tpl where ecp.jid=?2 and ecp.config_point=?1 and ecp.id=tpl.config_point_id ", nativeQuery = true)
    List<EmailTemplate> findByConfigPointAndJid(String configPoint, long jid);

    EmailTemplate findById(long id);

    @Transactional
	@Modifying
	@Query(value = "delete from  email_tpl where id=?1 ", nativeQuery = true)
    void delById(long id);
    
    @Transactional
	@Modifying
	@Query(value = "update  email_tpl set default_tpl = false where config_point_id=?1 ", nativeQuery = true)
    void clearDefault(long cpid);

    @Transactional
	@Modifying
	@Query(value = "update  email_tpl set default_tpl = true where id=?1 ", nativeQuery = true)
    void setDefault(long id);

    @Transactional
	@Modifying
	@Query(value = "update  email_tpl set json_data = ?2 where id=?1 ", nativeQuery = true)
    void update(long id, String json);
}
