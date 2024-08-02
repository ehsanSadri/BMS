package com.sadri.bms.model.dao;

import com.sadri.bms.model.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionDao extends JpaRepository<TransactionEntity, Long> {

}