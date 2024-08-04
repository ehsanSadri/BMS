package com.sadri.bms.common.dto.user;

import com.sadri.bms.model.entity.UsersEntity;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserIn {

    @NotNull
    private String name;
    @NotNull
    private String lastname;
    @NotNull
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