package com.sadri.bms.controller.account.pub;

import com.sadri.bms.common.dto.account.DepositIn;
import com.sadri.bms.common.dto.account.TransferIn;
import com.sadri.bms.common.dto.account.WithdrawIn;
import com.sadri.bms.common.dto.account.AccountOut;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${rest.pub}")
public class PublicAccountController {

    @GetMapping(path = "/account")
    public AccountOut getAccountInfo() {

        return null;
    }

    @PutMapping(path = "/account/withdraw")
    public AccountOut withdraw(@RequestBody WithdrawIn withdraw) {

        return null;
    }

    @PutMapping(path = "/account/deposit")
    public AccountOut deposit(@RequestBody DepositIn deposit) {
        return null;
    }

    @PutMapping(path = "/account/transfer")
    public AccountOut transfer(@RequestBody TransferIn transfer) {
        return null;
    }
}