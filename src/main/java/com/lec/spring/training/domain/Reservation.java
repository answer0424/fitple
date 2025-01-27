package com.lec.spring.training.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "Reservation")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "trainingId", nullable = false)
    @ToString.Exclude
    @JsonIgnore
    private Training training;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDate date;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReservationStatus status = ReservationStatus.운동전;;

    @Column(name = "startTime")
    @Temporal(TemporalType.TIME)
    private LocalDateTime startTime;

    @Column
    private Integer exerciseTime;
}
