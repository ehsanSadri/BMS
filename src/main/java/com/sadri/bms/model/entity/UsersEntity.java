package com.sadri.bms.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "users")
public class UsersEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator ="USERS_SEQ")
    @SequenceGenerator(name = "USERS_SEQ", sequenceName = "USERS_SEQ", allocationSize = 1)
    private Long id;

    @Column(name = "name", columnDefinition = "nvarchar(250)")
    private String name;

    @Column(name = "lastname", columnDefinition = "nvarchar(250)")
    private String lastname;

    @Column(name = "national_code", columnDefinition = "nvarchar(10)")
    private String nationalCode;

    @Column(name = "birth_date", columnDefinition = "timestamp")
    private LocalDateTime birthDate;

    @Column(name = "role", columnDefinition = "nvarchar(250)")
    private String role;

    @Column(name = "account_id_fk")
    private Long accountId;

    @JoinColumn(name = "account_id_fk", updatable = false, insertable = false)
    @OneToOne(fetch = FetchType.LAZY)
    private AccountEntity account;

}