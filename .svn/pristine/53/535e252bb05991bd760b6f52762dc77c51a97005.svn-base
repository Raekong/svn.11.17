package edu.nuist.ojs.baseinfo.entity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PublisherReps extends JpaRepository<Publisher, Long> {

    Publisher findById(long id);


    Publisher findByAbbrAndDisable(String abbr , boolean disable );

    Publisher findByAbbr(String abbr );

    Publisher save(Publisher publisher);

    Page<Publisher> findAll(Pageable pq);


}
