package com.lec.spring.training.repository;

import com.lec.spring.training.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByTraining(Long trainingid);

    int save(Review review);

    int deleteById(Long id);
}






