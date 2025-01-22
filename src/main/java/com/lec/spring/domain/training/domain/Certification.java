package com.lec.spring.domain.training.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Certification {

    @EmbeddedId
    private CertificationId id;

    @MapsId("trainerProfileId") // 복합 키의 trainerProfileId와 매핑
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TrainerProfile_id", nullable = false)
    private TrainerProfile trainerProfile;

    @Column(name = "credentials")
    private String credentials;
}


