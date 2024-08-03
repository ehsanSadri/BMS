package com.sadri.bms.model.dao;

import com.sadri.bms.model.entity.AccountEntity;
import jakarta.persistence.LockModeType;
import jakarta.persistence.QueryHint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.transaction.annotation.Transactional;

public interface AccountDao extends JpaRepository<AccountEntity, Long> {


    @Transactional(rollbackFor = Exception.class)
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @QueryHints({@QueryHint(name = "jakarta.persistence.lock.timeout", value = "15000")})
    @Query(value = "select a from AccountEntity a where a.id = :accountId")
    AccountEntity findByIdWithLock(Long accountId);

}