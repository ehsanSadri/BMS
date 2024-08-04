package com.sadri.bms.controller.account;

import com.sadri.bms.common.dto.PageableFilter;
import com.sadri.bms.common.dto.account.*;
import com.sadri.bms.model.service.account.AccountService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Validated
@AllArgsConstructor
@RestController
@RequestMapping("${rest.admin}")
public class AccountController {

    private final AccountService service;

    @GetMapping(path = "/account")
    public ResponseEntity<List<AccountOut>> getAll(@Valid PageableFilter filter) {
        return ResponseEntity.ok(service.getAll(filter));
    }

    @GetMapping(path = "/account/user/{userId}")
    public ResponseEntity<AccountOut> getByUserId(@PathVariable(name = "userId") Long userId) {
        return ResponseEntity.ok(service.getByUserId(userId));
    }

    @PostMapping(path = "/account/user/{userId}")
    public ResponseEntity<AccountOut> create(@PathVariable(name = "userId") Long userId,
                                             @Valid @RequestBody AccountIn account) throws ExecutionException, InterruptedException {
        return ResponseEntity.ok(service.create(account, userId));
    }

    @PutMapping("/account/{accountId}/deposit")
    public ResponseEntity<Boolean> deposit(@Valid @RequestBody TransactionIn model,
                                           @PathVariable(name = "accountId") Long accountId) throws ExecutionException, InterruptedException {
        return ResponseEntity.ok(service.deposit(accountId, model));
    }

    @PutMapping("/account/{accountId}/withdraw")
    public ResponseEntity<Boolean> withdraw(@Valid @RequestBody TransactionIn model,
                                            @PathVariable(name = "accountId") Long accountId) throws ExecutionException, InterruptedException {
        return ResponseEntity.ok(service.withdraw(accountId, model));
    }

    @PutMapping("/account/transfer")
    public ResponseEntity<Boolean> transfer(@Valid @RequestBody TransferIn model) throws ExecutionException, InterruptedException {
        return ResponseEntity.ok(service.transfer(model));
    }

    @GetMapping(path = "/account/{accountId}/balance")
    public ResponseEntity<BigDecimal> getAccountBalance(@PathVariable(name = "accountId") Long accountId) throws ExecutionException, InterruptedException {
        return ResponseEntity.ok(service.getAccountBalance(accountId));
    }

    @GetMapping(path = "/account/{accountId}/transaction-report")
    public ResponseEntity<List<TransactionOut>> getAccountTransactionReport(@PathVariable(name = "accountId") Long accountId,
                                                                            @Valid PageableFilter filter) {
        return ResponseEntity.ok(service.getTransactionReport(filter, accountId));
    }
}