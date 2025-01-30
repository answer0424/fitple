package com.lec.spring.training.repository;

import com.lec.spring.training.domain.GrantStatus;
import com.lec.spring.training.domain.TrainerProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TrainerProfileRepository extends JpaRepository<TrainerProfile, Long> {

    Optional<TrainerProfile> findByTrainerId(Long trainerId);


    List<TrainerProfile> findByIsAccess(GrantStatus isAccess);
}
