package com.sadri.bms.common.dto.account;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class WithdrawIn {

    private BigDecimal amount;
    private String accountNumber;
    private Byte accountPassword;
}