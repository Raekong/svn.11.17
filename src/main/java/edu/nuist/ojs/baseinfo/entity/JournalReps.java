package edu.nuist.ojs.baseinfo.entity;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface JournalReps extends JpaRepository<Journal,Long>{

	@Query(value = "select * from journal where pid=:pid order by j_order  ",nativeQuery = true)
	public List<Journal> findByPid( long pid );

	public Journal findById( long id );

	
	@Query(value = "select * from journal where is_complete=true",nativeQuery = true)
	public List<Journal> findAllAvaliableJournal();
	
	@Query(value = "select * from journal order by j_order ",nativeQuery = true)
	public List<Journal> findAllByOrder();
	
	public List<Journal> findByAbbreviationAndPid(String abbreviation, long pid);


	public Journal save(Journal j);

	@Transactional
	@Modifying
	@Query(value = "update journal set j_order=?2 where journal_id=?1 ", nativeQuery = true)
	public void updateOrder(long id,  double order);


	@Query(value = "select * from journal where pid=?1 and abbreviation like concat('%' ,?2,'%'	)  limit 0, 30",nativeQuery = true)
    public List<Journal> querybyAbbrLike(long pid , String abbreviation);


}
