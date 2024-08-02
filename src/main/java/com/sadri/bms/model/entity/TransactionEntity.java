package com.sadri.bms.model.entity;

import com.sadri.bms.model.entity.enums.TransactionMode;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "transaction")
public class TransactionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TRANSACTION_SEQ")
    @SequenceGenerator(name = "TRANSACTION_SEQ", sequenceName = "TRANSACTION_SEQ", allocationSize = 1)
    private Long id;

    @Column(name = "title", columnDefinition = "nvarchar(250)")
    private String title;

    @Column(name = "amount", columnDefinition = "numeric")
    private BigDecimal amount;

    @Enumerated(value = EnumType.STRING)
    @JdbcTypeCode(SqlTypes.ENUM)
    @Column(name = "transaction_mode", columnDefinition = "transaction_mode")
    private TransactionMode transactionMode;

    @Column(name = "account_id_fk")
    private Long accountId;

    @JoinColumn(name = "account_id_fk", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private AccountEntity account;
}