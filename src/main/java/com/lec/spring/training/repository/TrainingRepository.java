package com.lec.spring.training.repository;

import com.lec.spring.training.domain.Training;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrainingRepository extends JpaRepository<Training, Long> {

    int findByUserIdAndTrainerIdEquals(Long studentId, Long trainerId);


}// end TrainingRepository
