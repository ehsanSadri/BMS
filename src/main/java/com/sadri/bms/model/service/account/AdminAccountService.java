package com.sadri.bms.model.service.account;

import com.sadri.bms.common.config.TokenProvider;
import com.sadri.bms.common.dto.account.AccountIn;
import com.sadri.bms.common.dto.account.AccountOut;
import com.sadri.bms.common.dto.account.TransactionIn;
import com.sadri.bms.common.dto.account.TransferIn;
import com.sadri.bms.model.dao.AccountDao;
import com.sadri.bms.model.entity.AccountEntity;
import com.sadri.bms.model.entity.enums.TransactionMode;
import com.sadri.bms.model.service.transaction.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@Service
public class AdminAccountService {

    private final AccountDao dao;
    private final TransactionService transactionService;
    private final TokenProvider tokenProvider;

    @Transactional
    public AccountOut create(AccountIn model, Long userId) {
        AccountEntity account = new AccountEntity();
        account.setCreated(LocalDateTime.now());
        account.setUserId(userId);
        account.setTitle("حساب جاری نزد کاربر شماره " + userId);
        account.setPassword(tokenProvider.getToken(model.getAccountPassword()));
        AccountEntity savedAccount = dao.save(account);

        transactionService.makeTransaction(savedAccount, model.getInitBalance(), TransactionMode.INCREASE);
        return new AccountOut(savedAccount);
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean deposit(Long accountId, TransactionIn model) {
        boolean result = true;
        AccountEntity entity = dao.findByIdWithLock(accountId);
        transactionService.makeTransaction(entity, model, TransactionMode.INCREASE);

        return result;
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean withdraw(Long accountId, TransactionIn model) throws InterruptedException {
        boolean result = true;
        AccountEntity entity = dao.findByIdWithLock(accountId);

        BigDecimal accountBalance = transactionService.getBalanceByAccountId(accountId);
        if (accountBalance.compareTo(model.getAmount()) >= 0) {
            transactionService.makeTransaction(entity, model, TransactionMode.DECREASE);
        } else {
            //TODO: throw standard error here.
            System.err.println("out of balance");
            result = false;
        }
        return result;
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean transfer(TransferIn model) {
        boolean result = true;
        AccountEntity sourceEntity = dao.findByIdWithLock(model.getSourceAccountId());
        AccountEntity destinationEntity = dao.findByIdWithLock(model.getDestinationAccountId());

        BigDecimal sourceAccountBalance = transactionService.getBalanceByAccountId(model.getSourceAccountId());

        if (sourceAccountBalance.compareTo(model.getAmount()) >= 0) {
            transactionService.makeTransaction(sourceEntity, model.getAmount(), TransactionMode.DECREASE);
            transactionService.makeTransaction(destinationEntity, model.getAmount(), TransactionMode.INCREASE);
        } else {
            System.err.println("out of balance");
            result = false;
        }
        return result;
    }

    public BigDecimal getAccountBalance(Long accountId) {
        return transactionService.getBalanceByAccountId(accountId);
    }


}