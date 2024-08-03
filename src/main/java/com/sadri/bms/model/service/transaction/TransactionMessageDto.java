package com.sadri.bms.model.service.transaction;

import com.sadri.bms.model.entity.enums.TransactionMode;
import com.sadri.bms.model.entity.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TransactionMessageDto {

    private TransactionMode transactionMode;
    private TransactionType type;
    private BigDecimal amount;
    private String title;
    private LocalDateTime created;
}