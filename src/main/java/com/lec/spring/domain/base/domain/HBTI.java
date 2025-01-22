package com.lec.spring.domain.base.domain;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "HBTI")
@Data
public class HBTI {
    @Id
    private Long userId;

    @OneToOne
    @JoinColumn(name = "UserId", nullable = false)
    private User user;

    @Column(nullable = false, length = 4)
    private String hbti;

    @Column(nullable = false)
    private Double col1;

    @Column(nullable = false)
    private Double col2;

    @Column(nullable = false)
    private Double col3;

    @Column(nullable = false)
    private Double col4;
}
