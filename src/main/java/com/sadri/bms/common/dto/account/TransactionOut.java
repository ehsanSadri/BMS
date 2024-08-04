package com.sadri.bms.common.dto.account;

import com.sadri.bms.model.entity.TransactionEntity;
import com.sadri.bms.model.entity.enums.TransactionMode;
import com.sadri.bms.model.entity.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
public class TransactionOut extends TransactionIn {

    private String title;
    private LocalDateTime created;
    private Long accountId;
    private TransactionMode mode;
    private TransactionType type;

    public TransactionOut(TransactionEntity entity) {
        if (entity != null) {
            this.setAmount(entity.getAmount());
            this.title = entity.getTitle();
            this.created = entity.getCreated();
            this.accountId = entity.getAccountId();
            this.mode = entity.getTransactionMode();
        }
    }

}