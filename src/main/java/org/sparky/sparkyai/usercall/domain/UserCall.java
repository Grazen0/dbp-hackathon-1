package org.sparky.sparkyai.usercall.domain;

import java.time.ZonedDateTime;

import org.hibernate.annotations.CreationTimestamp;

import org.sparky.sparkyai.company.domain.Company;
import org.sparky.sparkyai.user.domain.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class UserCall {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Company company;

    @Column(nullable = false)
    private String prompt;

    @Column(nullable = false)
    private String response;

    @Column(nullable = false)
    private Boolean wasError;

    @Column(nullable = false)
    private Long consumedTokens;

    @CreationTimestamp
    @Column(nullable = false)
    private ZonedDateTime createdAt;

}
