package com.sadri.bms.model.dao;

import com.sadri.bms.model.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AccountDao extends JpaRepository<AccountEntity, Long> {

    @Query(value = "select a.locked from AccountEntity a where a.id = :accountId")
    boolean getLockById(@Param(value = "accountId") Long accountId);

    @Modifying(flushAutomatically = true)
    @Query(value = "UPDATE AccountEntity a SET a.locked = :lock where a.id = :accountId")
    void setLockById(@Param(value = "accountId") Long accountId,
                     @Param(value = "lock") boolean lock);
}