package com.sadri.bms.model.service.user;

import com.sadri.bms.common.dto.PageableFilter;
import com.sadri.bms.common.dto.user.UserIn;
import com.sadri.bms.common.dto.user.UserOut;
import com.sadri.bms.model.dao.UserDao;
import com.sadri.bms.model.entity.UsersEntity;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class UserService {

    private final UserDao userDao;

    public UserOut create(UserIn model, String role) {
        UsersEntity entity = model.convertToEntity(new UsersEntity());
        entity.setRole(role);
        return new UserOut(userDao.save(entity));
    }

    public UserOut getById(Long id) {
        return new UserOut(userDao.getUsersEntityById(id));
    }

    public List<UserOut> getAll(PageableFilter filter) {
        Pageable pageable = PageRequest.of(filter.getPage() - 1, filter.getSize());
        return userDao.findAll(pageable).stream().map(UserOut::new).collect(Collectors.toList());
    }

}