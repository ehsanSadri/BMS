package com.sadri.bms.model.service.account;

import com.sadri.bms.common.config.TokenProvider;
import com.sadri.bms.common.dto.account.AccountIn;
import com.sadri.bms.common.dto.account.AccountOut;
import com.sadri.bms.common.dto.account.TransactionIn;
import com.sadri.bms.common.dto.account.TransferIn;
import com.sadri.bms.model.dao.AccountDao;
import com.sadri.bms.model.entity.AccountEntity;
import com.sadri.bms.model.entity.enums.TransactionMode;
import com.sadri.bms.model.entity.enums.TransactionType;
import com.sadri.bms.model.service.ExecutorCallerService;
import com.sadri.bms.model.service.transaction.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

@AllArgsConstructor
@Service
public class AdminAccountService {

    private final AccountDao dao;
    private final TransactionService transactionService;
    private final TokenProvider tokenProvider;
    private final ExecutorCallerService executorCallerService;

    @Transactional
    public AccountOut create(AccountIn model, Long userId) throws ExecutionException, InterruptedException {
        Callable<AccountOut> createCallable = () -> {
            AccountEntity account = new AccountEntity();
            account.setCreated(LocalDateTime.now());
            account.setUserId(userId);
            account.setTitle("حساب جاری نزد کاربر شماره " + userId);
            account.setPassword(tokenProvider.getToken(model.getAccountPassword()));
            AccountEntity savedAccount = dao.save(account);

            transactionService.makeTransaction(savedAccount, model.getInitBalance(), TransactionMode.INCREASE, TransactionType.INITIAL);
            return new AccountOut(savedAccount);
        };
        return executorCallerService.execute(createCallable).get();
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean deposit(Long accountId, TransactionIn model) throws ExecutionException, InterruptedException {
        Callable<Boolean> deposit = () -> {
            AccountEntity entity = dao.findByIdWithLock(accountId);
            transactionService.makeTransaction(entity, model, TransactionMode.INCREASE, TransactionType.DEPOSIT);
            return Boolean.TRUE;
        };
        return executorCallerService.execute(deposit).get();
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean withdraw(Long accountId, TransactionIn model) throws ExecutionException, InterruptedException {
        Callable<Boolean> withdraw = () -> {
            AccountEntity entity = dao.findByIdWithLock(accountId);

            BigDecimal accountBalance = transactionService.getBalanceByAccountId(accountId);
            if (accountBalance.compareTo(model.getAmount()) >= 0) {
                transactionService.makeTransaction(entity, model, TransactionMode.DECREASE, TransactionType.WITHDRAW);
            } else {
                System.err.println("out of balance");
                return Boolean.FALSE;
            }
            return Boolean.TRUE;
        };

        return executorCallerService.execute(withdraw).get();
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean transfer(TransferIn model) throws ExecutionException, InterruptedException {
        Callable<Boolean> transfer = () -> {
            AccountEntity sourceEntity = dao.findByIdWithLock(model.getSourceAccountId());
            AccountEntity destinationEntity = dao.findByIdWithLock(model.getDestinationAccountId());

            BigDecimal sourceAccountBalance = transactionService.getBalanceByAccountId(model.getSourceAccountId());

            if (sourceAccountBalance.compareTo(model.getAmount()) >= 0) {
                transactionService.makeTransaction(sourceEntity, model.getAmount(), TransactionMode.DECREASE, TransactionType.TRANSFER);
                transactionService.makeTransaction(destinationEntity, model.getAmount(), TransactionMode.INCREASE, TransactionType.TRANSFER);
            } else {
                System.err.println("out of balance");
                return Boolean.FALSE;
            }
            return Boolean.TRUE;
        };

        return executorCallerService.execute(transfer).get();
    }

    public BigDecimal getAccountBalance(Long accountId) throws ExecutionException, InterruptedException {
        Callable<BigDecimal> balanceCallable = () -> transactionService.getBalanceByAccountId(accountId);
        return executorCallerService.execute(balanceCallable).get();
    }


}