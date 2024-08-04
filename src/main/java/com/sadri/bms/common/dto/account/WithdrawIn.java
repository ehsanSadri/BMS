package com.sadri.bms.common.dto.account;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class WithdrawIn {

    @NotNull
    private BigDecimal amount;
    @NotNull
    private String accountNumber;
    private Byte accountPassword;
}