package com.sadri.bms.common.dto.account;

import lombok.Getter;
import lombok.Setter;


import java.math.BigDecimal;

@Getter
@Setter
public class AccountIn {

    private BigDecimal initBalance;
    private String accountPassword;
}