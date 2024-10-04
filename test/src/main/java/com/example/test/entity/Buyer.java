package com.example.test.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.rest.core.annotation.RestResource;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "buyers")
@RestResource(rel = "buyers", path = "buyers")
public class Buyer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", length = 20)
    private String name;

    @Column(name = "trade_time")
    private Instant tradeTime;

    @Column(name = "trade_addr", length = 30)
    private String tradeAddr;

    @Column(name = "status", length = 10)
    private String status;

}