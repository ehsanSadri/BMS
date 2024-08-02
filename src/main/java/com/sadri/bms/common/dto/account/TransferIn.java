package com.sadri.bms.common.dto.account;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class TransferIn {

    private BigDecimal amount;
    private Long sourceAccountId;
    private Long destinationAccountId;
    private String sourceAccountPassword;
}