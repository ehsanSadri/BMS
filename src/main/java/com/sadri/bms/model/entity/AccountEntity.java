package com.sadri.bms.model.entity;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "account")
public class AccountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ACCOUNT_SEQ")
    @SequenceGenerator(name = "ACCOUNT_SEQ", sequenceName = "ACCOUNT_SEQ", allocationSize = 1)
    private Long id;

    @Column(name = "title", columnDefinition = "nvarchar(250)")
    private String title;

    @Column(name = "created", columnDefinition = "timestamp")
    private LocalDateTime created;

    @Column(name = "account_number", unique = true, columnDefinition = "varchar(5)")
    private String accountNumber;

    @Column(name = "user_id_fk")
    private Long userId;

    @JoinColumn(name = "user_id_fk", updatable = false, insertable = false)
    @OneToOne(fetch = FetchType.LAZY)
    private UsersEntity user;

    @JoinColumn(name = "account_id_fk")
    @OneToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    private List<TransactionEntity> transactionList;


    @PrePersist
    @PostConstruct
    public void formatNumber() {
        setAccountNumber(String.format("%05d", this.id));
    }
}
