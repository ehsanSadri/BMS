package com.sadri.bms.model.service.user;

import com.sadri.bms.common.dto.user.UserIn;
import com.sadri.bms.common.dto.user.UserOut;
import com.sadri.bms.model.dao.UserDao;
import com.sadri.bms.model.entity.UsersEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class AdminUserService {

    private final UserDao userDao;

    public UserOut create(UserIn model, String role) {
        UsersEntity entity = model.convertToEntity(new UsersEntity());
        entity.setRole(role);
        return new UserOut(userDao.save(entity));
    }

}