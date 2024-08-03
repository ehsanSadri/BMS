package com.sadri.bms.model.dao;

import com.sadri.bms.model.entity.AccountEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.QueryHint;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public interface AccountDao extends JpaRepository<AccountEntity, Long> {

//    @PersistenceContext
//    private final EntityManager entityManager;
//
//    public AccountDao(EntityManager entityManager) {
//        this.entityManager = entityManager;
//    }

//    @Query(value = "select a.locked from AccountEntity a where a.id = :accountId")
//    boolean getLockById(@Param(value = "accountId") Long accountId);
//
//    @Modifying(flushAutomatically = true)
//    @Query(value = "UPDATE AccountEntity a SET a.locked = :lock where a.id = :accountId")
//    void setLockById(@Param(value = "accountId") Long accountId,
//                     @Param(value = "lock") boolean lock);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value = "15000")})
    @Query(value = "select a from AccountEntity a where a.id = :accountId")
    AccountEntity findByIdWithLock(Long accountId);

//    public AccountEntity findAndLockById(Long id) {
//        Map<String, Object> properties = new HashMap<>();
//        properties.put("jakarta.persistence.lock.timeout", 20000L);
//        return entityManager.find(AccountEntity.class, id, LockModeType.PESSIMISTIC_READ, properties);
//    }
//
//    public AccountEntity save(AccountEntity entity) {
//        return entityManager.merge(entity);
//    }

}