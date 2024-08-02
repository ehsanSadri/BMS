package com.sadri.bms.model.service.transaction;

import com.sadri.bms.common.dto.account.TransactionIn;
import com.sadri.bms.model.dao.TransactionDao;
import com.sadri.bms.model.entity.AccountEntity;
import com.sadri.bms.model.entity.TransactionEntity;
import com.sadri.bms.model.entity.enums.TransactionMode;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;


@AllArgsConstructor
@Service
public class TransactionService {

    private final TransactionDao dao;

    @Transactional(rollbackFor = Exception.class)
    public void makeTransaction(AccountEntity account,
                                TransactionIn model,
                                TransactionMode mode) {
        TransactionEntity transaction = new TransactionEntity();
        transaction.setAccountId(account.getId());
        transaction.setTransactionMode(mode);
        transaction.setAmount(model.getAmount());
        dao.save(transaction);
        dao.flush();
    }

    @Transactional
    public void makeTransaction(AccountEntity account,
                                BigDecimal amount,
                                TransactionMode mode) {
        TransactionEntity transaction = new TransactionEntity();
        transaction.setAccountId(account.getId());
        transaction.setTransactionMode(mode);
        transaction.setAmount(amount);
        dao.save(transaction);
        dao.flush();
    }

    public BigDecimal getBalanceByAccountId(Long AccountId) {
        return dao.getBalanceByAccountId(AccountId);
    }
}