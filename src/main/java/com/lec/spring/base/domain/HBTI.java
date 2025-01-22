package com.lec.spring.base.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "HBTI")
public class HBTI {
    @Id
    private Long userId;

    @OneToOne
    @JoinColumn(name = "UserId", nullable = false)
    private User user;

    @Column(nullable = false, length = 4)
    private String hbti;

    @Column(nullable = false)
    private Double mbScore;

    @Column(nullable = false)
    private Double eiScore;

    @Column(nullable = false)
    private Double cnScore;

    @Column(nullable = false)
    private Double pgScore;
}
