package com.sadri.bms.model.service.transaction;

import com.sadri.bms.common.dto.account.TransactionIn;
import com.sadri.bms.model.dao.TransactionDao;
import com.sadri.bms.model.entity.AccountEntity;
import com.sadri.bms.model.entity.TransactionEntity;
import com.sadri.bms.model.entity.enums.TransactionMode;
import com.sadri.bms.model.service.ExecutorCallerService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;


@AllArgsConstructor
@Service
public class TransactionService {

    private final TransactionDao dao;
    private final ExecutorCallerService executorCallerService;

    @Transactional(rollbackFor = Exception.class)
    public void makeTransaction(AccountEntity account,
                                TransactionIn model,
                                TransactionMode mode) {
        Callable<Boolean> transactionCallable = () -> {
            TransactionEntity transaction = new TransactionEntity();
            transaction.setAccountId(account.getId());
            transaction.setTransactionMode(mode);
            transaction.setAmount(model.getAmount());
            dao.save(transaction);
            dao.flush();
            return true;
        };

        executorCallerService.execute(transactionCallable);
    }

    @Transactional
    public void makeTransaction(AccountEntity account,
                                BigDecimal amount,
                                TransactionMode mode) {
        Callable<Boolean> transactionCallable = () -> {
            TransactionEntity transaction = new TransactionEntity();
            transaction.setAccountId(account.getId());
            transaction.setTransactionMode(mode);
            transaction.setAmount(amount);
            dao.save(transaction);
            dao.flush();
            return true;
        };

        executorCallerService.execute(transactionCallable);
    }

    public BigDecimal getBalanceByAccountId(Long AccountId) throws ExecutionException, InterruptedException {
        Callable<BigDecimal> transactionCallable = () -> dao.getBalanceByAccountId(AccountId);
        return executorCallerService.execute(transactionCallable).get();
    }
}