package com.lec.spring.training.controller;

import com.lec.spring.training.DTO.ReviewResponseDTO;
import com.lec.spring.training.domain.Review;
import com.lec.spring.training.service.ReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {
    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("/training/{trainingId}")
    public ResponseEntity<List<ReviewResponseDTO>> getReviews(@PathVariable Long trainingId) {
        return ResponseEntity.ok(reviewService.getReviews(trainingId));
    }

    @PostMapping("/training/{trainingId}")
    public ResponseEntity<Review> createReview(
            @PathVariable Long trainingId,
            @RequestBody Review review) {
        return ResponseEntity.ok(reviewService.createReview(trainingId, review));
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long reviewId) {
        reviewService.deleteReview(reviewId);
        return ResponseEntity.noContent().build();
    }
}
