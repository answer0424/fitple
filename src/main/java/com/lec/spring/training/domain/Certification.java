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
    private CertificationId Id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TrainerProfile_id", insertable = false, updatable = false)
    @ToString.Exclude
    @JsonIgnore
    private TrainerProfile trainerProfile;

    @Column(name = "credentials")
    private String credentials;
}

