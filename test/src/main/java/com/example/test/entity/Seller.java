package com.example.test.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "sellers")
public class Seller {
    @Id
    @Column(name = "account", nullable = false, length = 20)
    private String account;

    @Column(name = "password", length = 20)
    private String password;

}