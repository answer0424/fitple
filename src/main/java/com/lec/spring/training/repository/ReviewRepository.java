package com.lec.spring.training.repository;

import com.lec.spring.training.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Query("SELECT r FROM Review r WHERE r.training.id = :trainingId ORDER BY r.createdAt DESC")
    List<Review> findReviews(@Param("trainingId") Long trainingId);
}






