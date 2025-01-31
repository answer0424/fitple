package com.lec.spring.training.domain;

import com.lec.spring.base.domain.User;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

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

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate career;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private GrantStatus isAccess = GrantStatus.valueOf("대기");

    @OneToMany(mappedBy = "trainerProfile", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @Builder.Default
    private List<Certification> certificationList = new ArrayList<>();

    public void addCertificationList(Certification... certificationList) {
        Collections.addAll(this.certificationList, certificationList);
    }
}

