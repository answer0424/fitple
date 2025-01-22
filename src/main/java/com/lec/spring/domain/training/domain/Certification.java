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
    private CertificationId Id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TrainerProfile_id", insertable = false, updatable = false)
    private TrainerProfile trainerProfile;

    @Column(name = "credentials")
    private String credentials;
}

