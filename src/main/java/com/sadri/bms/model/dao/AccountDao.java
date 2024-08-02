package com.sadri.bms.model.dao;

import com.sadri.bms.model.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountDao extends JpaRepository<AccountEntity, Long> {
}
