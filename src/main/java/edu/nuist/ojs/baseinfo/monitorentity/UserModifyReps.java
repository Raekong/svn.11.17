package edu.nuist.ojs.baseinfo.monitorentity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserModifyReps extends JpaRepository<UserModify,Long> {
    UserModify findByUserId(long userId);
    UserModify save(UserModify userModify);
}
