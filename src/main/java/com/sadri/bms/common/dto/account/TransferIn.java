package com.sadri.bms.common.dto.account;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class TransferIn {

    @NotNull
    private BigDecimal amount;
    @NotNull
    private Long sourceAccountId;
    @NotNull
    private Long destinationAccountId;
    private String sourceAccountPassword;
}