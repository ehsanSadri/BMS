package com.sadri.bms.model.service.account;

import com.sadri.bms.common.dto.account.AccountIn;
import com.sadri.bms.common.dto.account.AccountOut;
import com.sadri.bms.model.dao.AccountDao;
import com.sadri.bms.model.dao.TransactionDao;
import com.sadri.bms.model.entity.AccountEntity;
import com.sadri.bms.model.entity.TransactionEntity;
import com.sadri.bms.model.entity.enums.TransactionMode;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@AllArgsConstructor
@Service
public class AdminAccountService {

    private final AccountDao accountDao;
    private final TransactionDao transactionDao;

    @Transactional
    public AccountOut create(AccountIn model, Long userId) {
        AccountEntity account = new AccountEntity();
        account.setCreated(LocalDateTime.now());
        account.setUserId(userId);
        account.setTitle("حساب جاری نزد کاربر شماره " + userId);
        AccountEntity savedAccount = accountDao.save(account);

        TransactionEntity transaction = new TransactionEntity();
        transaction.setAccountId(savedAccount.getId());
        transaction.setTitle("test");
        transaction.setTransactionMode(TransactionMode.INCREASE);
        transaction.setAmount(model.getInitBalance());

        transactionDao.save(transaction);

        return new AccountOut(savedAccount);
    }

}