package com.sadri.bms.common.dto.user;

import com.sadri.bms.common.dto.account.AccountOut;
import com.sadri.bms.model.entity.UsersEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

@NoArgsConstructor
@Getter
@Setter
public class UserOut extends UserIn {

    private Long id;
    private Long accountId;
    private String fullName;
    private AccountOut account;

    public UserOut(UsersEntity entity) {
        this.id = entity.getId();
        this.accountId = entity.getAccountId();
        this.fullName = entity.getName() + " " + entity.getLastname();
        this.setName(entity.getName());
        this.setLastname(entity.getLastname());
        this.setNationalCode(entity.getNationalCode());
        this.setBirthDate(entity.getBirthDate());

        if (Hibernate.isInitialized(entity.getAccount()) && entity.getAccount() != null) {
            account = new AccountOut(entity.getAccount());
        }
    }
}