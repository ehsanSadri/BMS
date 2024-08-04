package com.sadri.bms.model.service.logger;

import com.sadri.bms.model.service.observer.Observer;
import com.sadri.bms.model.service.transaction.TransactionMessageDto;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

@Service
public class LoggerService implements Observer {

    private BufferedWriter writer;

    @Override
    public void update(Object message) {
        if (message instanceof TransactionMessageDto transactionMessage) {
            try {
                if (writer != null) {
                    writer.write(
                            transactionMessage.getTitle() +
                            " - type: " +
                            transactionMessage.getType().name() +
                            " - amount: " +
                            transactionMessage.getAmount() +
                            " - mode: " +
                            transactionMessage.getTransactionMode().name());
                    writer.newLine();
                    writer.flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @PostConstruct
    public void init() {
        try {
            writer = new BufferedWriter(new FileWriter("log.txt", true));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @PreDestroy
    public void cleanup() {
        try {
            if (writer != null) {
                writer.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}