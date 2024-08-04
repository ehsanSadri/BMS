package com.sadri.bms.model.service.transaction;

import com.sadri.bms.common.dto.PageableFilter;
import com.sadri.bms.common.dto.account.TransactionIn;
import com.sadri.bms.common.dto.account.TransactionOut;
import com.sadri.bms.model.dao.TransactionDao;
import com.sadri.bms.model.entity.AccountEntity;
import com.sadri.bms.model.entity.TransactionEntity;
import com.sadri.bms.model.entity.enums.TransactionMode;
import com.sadri.bms.model.entity.enums.TransactionType;
import com.sadri.bms.model.service.ExecutorCallerService;
import com.sadri.bms.model.service.observer.Observer;
import com.sadri.bms.model.service.observer.Subject;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;


@AllArgsConstructor
@Service
public class TransactionService extends Subject {

    private final TransactionDao dao;
    private final ExecutorCallerService executorCallerService;

    @Transactional(rollbackFor = Exception.class)
    public void makeTransaction(AccountEntity account,
                                TransactionIn model,
                                TransactionMode mode,
                                TransactionType type) {
        Callable<Boolean> transactionCallable = () -> {
            TransactionEntity transaction = new TransactionEntity();
            transaction.setAccountId(account.getId());
            transaction.setTransactionMode(mode);
            transaction.setAmount(model.getAmount());
            transaction.setCreated(LocalDateTime.now());
            transaction.setTitle("for account no: " + account.getAccountNumber() + " at: " + transaction.getCreated());
            dao.save(transaction);
            dao.flush();

            TransactionMessageDto message = new TransactionMessageDto(
                    mode,
                    type,
                    transaction.getAmount(),
                    transaction.getTitle(),
                    transaction.getCreated()
            );

            notifyObservers(message);

            return true;
        };

        executorCallerService.execute(transactionCallable);
    }

    @Transactional
    public void makeTransaction(AccountEntity account,
                                BigDecimal amount,
                                TransactionMode mode,
                                TransactionType type) {
        Callable<Boolean> transactionCallable = () -> {
            TransactionEntity transaction = new TransactionEntity();
            transaction.setAccountId(account.getId());
            transaction.setTransactionMode(mode);
            transaction.setAmount(amount);
            transaction.setCreated(LocalDateTime.now());
            transaction.setTitle("for account no: " + account.getAccountNumber() + " at: " + transaction.getCreated());

            dao.save(transaction);
            dao.flush();

            TransactionMessageDto message = new TransactionMessageDto(
                    mode,
                    type,
                    amount,
                    transaction.getTitle(),
                    transaction.getCreated()
            );
            notifyObservers(message);

            return true;
        };

        executorCallerService.execute(transactionCallable);
    }

    public BigDecimal getBalanceByAccountId(Long AccountId) throws ExecutionException, InterruptedException {
        Callable<BigDecimal> transactionCallable = () -> dao.getBalanceByAccountId(AccountId);
        return executorCallerService.execute(transactionCallable).get();
    }

    public List<TransactionOut> getAllByAccountId(PageableFilter filter, Long accountId) {
        Pageable pageable = PageRequest.of(filter.getPage() - 1, filter.getSize());
        return dao.getAllByAccountId(accountId, pageable).stream().map(TransactionOut::new).collect(Collectors.toList());
    }

    @Override
    protected void notifyObservers(Object transaction) {
        for (Observer observer : super.getObservers()) {
            observer.update(transaction);
        }
    }
}