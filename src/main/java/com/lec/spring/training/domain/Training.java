package com.lec.spring.training.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lec.spring.base.domain.User;
import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "Training")
public class Training {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "UserId", nullable = false)
    @ToString.Exclude
    @JsonIgnore
    private User user;

    @ManyToOne
    @JoinColumn(name = "TrainerId", nullable = false)
    @ToString.Exclude
    @JsonIgnore
    private User trainer;

    @Column
    private Integer times;
}
