package com.sadri.bms.common.dto.account;

import com.sadri.bms.model.entity.AccountEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
public class AccountOut extends AccountIn {

    private Long id;
    private Long UserId;
    private String accountNumber;
    private LocalDateTime created;


    public AccountOut(AccountEntity entity) {
        this.id = entity.getId();
        this.UserId = entity.getUserId();
        this.accountNumber = entity.getAccountNumber();
        this.created = entity.getCreated();
    }


}