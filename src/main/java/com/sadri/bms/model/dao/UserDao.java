package com.sadri.bms.model.dao;

import com.sadri.bms.model.entity.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<UsersEntity, Long> {

    UsersEntity getUsersEntityById(Long id);
}
