package com.sadri.bms.common.dto.user;

import com.sadri.bms.model.entity.UsersEntity;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserIn {

    private String name;
    private String lastname;
    private String nationalCode;
    private LocalDateTime birthDate;

    public UsersEntity convertToEntity(UsersEntity entity) {
        entity.setName(this.name);
        entity.setLastname(this.lastname);
        entity.setNationalCode(this.nationalCode);
        entity.setBirthDate(this.birthDate);
        return entity;
    }

}