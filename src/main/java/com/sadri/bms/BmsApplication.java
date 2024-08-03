package com.sadri.bms;

import com.sadri.bms.model.service.logger.LoggerService;
import com.sadri.bms.model.service.transaction.TransactionService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BmsApplication implements CommandLineRunner {

    private final TransactionService transactionService;
    private final LoggerService loggerService;

    public BmsApplication(TransactionService transactionService, LoggerService loggerService) {
        this.transactionService = transactionService;
        this.loggerService = loggerService;
    }

    public static void main(String[] args) {
        SpringApplication.run(BmsApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        transactionService.addObserver(loggerService);
    }
}
