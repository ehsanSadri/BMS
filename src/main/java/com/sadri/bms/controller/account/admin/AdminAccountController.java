package com.sadri.bms.controller.account.admin;

import com.sadri.bms.common.dto.account.AccountIn;
import com.sadri.bms.common.dto.account.AccountOut;
import com.sadri.bms.common.dto.account.TransactionIn;
import com.sadri.bms.common.dto.account.TransferIn;
import com.sadri.bms.model.service.account.AdminAccountService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("${rest.admin}")
public class AdminAccountController {

    private final AdminAccountService service;

    @GetMapping(path = "/account")
    public AccountOut getAll() {

        return null;
    }

    @GetMapping(path = "/account/user/{userId}")
    public AccountOut getByUserId(@PathVariable(name = "userId") Long userId) {

        return null;
    }

    @PostMapping(path = "/account/user/{userId}")
    public ResponseEntity<AccountOut> create(@PathVariable(name = "userId") Long userId,
                                             @RequestBody AccountIn account) {
        return ResponseEntity.ok(service.create(account, userId));
    }

    @PutMapping("/account/{accountId}/deposit")
    public ResponseEntity<Boolean> deposit(@RequestBody TransactionIn model,
                                           @PathVariable(name = "accountId") Long accountId) {
        return ResponseEntity.ok(service.deposit(accountId, model));
    }

    @PutMapping("/account/{accountId}/withdraw")
    public ResponseEntity<Boolean> withdraw(@RequestBody TransactionIn model,
                                            @PathVariable(name = "accountId") Long accountId) {
        return ResponseEntity.ok(service.withdraw(accountId, model));
    }

    @PutMapping("/account/transfer")
    public ResponseEntity<Boolean> transfer(@RequestBody TransferIn model) {
        return ResponseEntity.ok(service.transfer(model));
    }
}