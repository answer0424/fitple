package com.lec.spring.training.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Certification {

    @EmbeddedId
    private CertificationId id;

    @MapsId("trainerProfileId") // 복합 키의 trainerProfileId와 매핑
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trainer_profile_id", nullable = false)
    @ToString.Exclude
    @JsonIgnore
    private TrainerProfile trainerProfile;

    @Column(name = "credentials")
    private String credentials;

    @Column(nullable = false)
    private String skills;
}

