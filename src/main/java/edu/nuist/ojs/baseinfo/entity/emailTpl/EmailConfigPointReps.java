package edu.nuist.ojs.baseinfo.entity.emailTpl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailConfigPointReps extends JpaRepository<EmailConfigPoint, Long> {
    EmailConfigPoint save(EmailConfigPoint ecp);

    EmailConfigPoint findById(long id);

    EmailConfigPoint findByConfigPointAndJid(String configPoint, long jid);

    List<EmailConfigPoint> findByJid(long jid);

    @Transactional
	@Modifying
	@Query(value = "update  email_config_point set email =?2 where id=?1 ", nativeQuery = true)
    void setWithEmail(long id, boolean flag);
}
