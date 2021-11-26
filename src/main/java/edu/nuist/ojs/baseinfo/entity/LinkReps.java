package edu.nuist.ojs.baseinfo.entity;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LinkReps extends JpaRepository<Link,Long> {
    Link findByMD5(String md5);

    @Transactional
	@Modifying
	@Query(value = "update link set is_closed=true where id=?1 ", nativeQuery = true)
	public void close(long id);

	@Transactional
	@Modifying
	@Query(value = "update link set is_closed=true where md5=?1 ", nativeQuery = true)
	public void close(String md5);

	@Query(value = "select * from link where json_data  like concat('%' ,?1,'%' ) limit 0, 1", nativeQuery = true)
	public Link findByRaidLike(String para);


}
