package com.sadri.bms.model.service.account;

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

    @Transactional
    public AccountOut create(AccountIn model, Long userId) {
        AccountEntity account = new AccountEntity();
        account.setCreated(LocalDateTime.now());
        account.setUserId(userId);
        account.setTitle("حساب جاری نزد کاربر شماره " + userId);
        AccountEntity savedAccount = dao.save(account);

        transactionService.makeTransaction(savedAccount, model.getInitBalance(), TransactionMode.INCREASE);
        return new AccountOut(savedAccount);
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean deposit(Long accountId, TransactionIn model) {
        boolean result = true;
        AccountEntity entity = dao.getReferenceById(accountId);
        if (!entity.isLocked()) {
            dao.setLockById(accountId, true);
            transactionService.makeTransaction(entity, model, TransactionMode.INCREASE);
            dao.setLockById(accountId, false);
        } else {
            System.err.println("account is locked");
            result = false;
        }
        return result;
    }

    @Transactional
    public boolean withdraw(Long accountId, TransactionIn model) {
        boolean result = true;
        AccountEntity entity = dao.getReferenceById(accountId);
        if (!entity.isLocked()) {
            dao.setLockById(accountId, true);
            BigDecimal accountBalance = transactionService.getBalanceByAccountId(accountId);

            if (accountBalance.compareTo(model.getAmount()) >= 0) {
                transactionService.makeTransaction(entity, model, TransactionMode.DECREASE);
            } else {
                //TODO: throw standard error here.
                System.err.println("out of balance");
                result = false;
            }

            dao.setLockById(accountId, false);
        } else {
            //TODO: throw standard error here.
            System.err.println("account is locked");
            result = false;
        }
        return result;
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean transfer(TransferIn model) {
        boolean result = true;
        AccountEntity sourceEntity = dao.getReferenceById(model.getSourceAccountId());
        AccountEntity destinationEntity = dao.getReferenceById(model.getDestinationAccountId());

        if (!sourceEntity.isLocked() && !destinationEntity.isLocked()) {
            dao.setLockById(model.getSourceAccountId(), true);
            dao.setLockById(model.getDestinationAccountId(), true);
            BigDecimal sourceAccountBalance = transactionService.getBalanceByAccountId(model.getSourceAccountId());

            if (sourceAccountBalance.compareTo(model.getAmount()) >= 0) {
                transactionService.makeTransaction(sourceEntity, model.getAmount(), TransactionMode.DECREASE);
                transactionService.makeTransaction(destinationEntity, model.getAmount(), TransactionMode.INCREASE);
            } else {
                System.err.println("out of balance");
                result = false;
            }


            dao.setLockById(model.getSourceAccountId(), true);
            dao.setLockById(model.getDestinationAccountId(), true);
        } else {
            System.err.println("concurrent use");
            result = false;
        }

        return result;
    }


}