package com.sadri.bms.common.dto.account;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class TransferIn {

    private BigDecimal amount;
    private String sourceAccountNumber;
    private String destinationAccountNumber;
    private String sourceAccountPassword;
}