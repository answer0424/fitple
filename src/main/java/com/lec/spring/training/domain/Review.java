package com.lec.spring.training.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lec.spring._common.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@DynamicUpdate
@DynamicInsert
@Entity(name = "Review")
public class Review extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "Training_id", nullable = false)
    @ToString.Exclude
    @JsonIgnore
    private Training training;

    @Column(nullable = false)
    private Byte rating;

    @Column
    private String content;
}

