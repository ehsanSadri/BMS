package com.sadri.bms.model.dao;

import com.sadri.bms.model.entity.TransactionEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface TransactionDao extends JpaRepository<TransactionEntity, Long> {

    @Query("""
                SELECT
                SUM(CASE WHEN t.transactionMode = 'INCREASE' THEN t.amount WHEN t.transactionMode = 'DECREASE' THEN -t.amount ELSE 0 END)
                FROM TransactionEntity t
                WHERE t.accountId = :accountId
            """)
    BigDecimal getBalanceByAccountId(@Param(value = "accountId") Long accountId);

    @Query(value = "select a from TransactionEntity a where a.accountId = :accountId order by a.created desc")
    List<TransactionEntity> getAllByAccountId(Long accountId, Pageable pageable);
}