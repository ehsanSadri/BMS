package com.sadri.bms.common.dto.account;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


import java.math.BigDecimal;

@Getter
@Setter
public class AccountIn {

    @NotNull
    private BigDecimal initBalance;
    @NotNull
    private String accountPassword;
}