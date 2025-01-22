package com.lec.spring.domain.training.domain;

import com.lec.spring.domain.base.domain.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "TrainerProfile")
public class TrainerProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "trainerId", nullable = false)
    private User trainer;

    @Column(nullable = false)
    private Integer perPrice;

    @Column(nullable = false, columnDefinition = "JSON")
    private String skills;

    @Column(nullable = false)
    private String content;

    @Column
    private Date career;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private GrantStatus grant;
}

