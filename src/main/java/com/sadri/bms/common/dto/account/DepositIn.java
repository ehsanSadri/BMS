package com.sadri.bms.common.dto.account;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class DepositIn {

    @NotNull
    private String accountNumber;
    @NotNull
    private BigDecimal amount;
}