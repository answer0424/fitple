package com.lec.spring.base.domain;

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
@Entity(name = "User")
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "GymId")
    @ToString.Exclude
    @JsonIgnore
    private Gym gym;

    @Column(nullable = false, length = 30)
    private String username;

    @Column(nullable = false, length = 50)
    private String password;

    @Column(nullable = false, length = 257)
    private String email;

    @Column(length = 200)
    private String address;

    @Column
    private Date birth;

    @Column(nullable = false, length = 45)
    private String nickname;

    @Column(length = 10)
    private String provider;

    @Column(length = 100)
    private String providerId;

    @Column(length = 20)
    private String authority;

    @Column(length = 50)
    private String profileImage;
}
