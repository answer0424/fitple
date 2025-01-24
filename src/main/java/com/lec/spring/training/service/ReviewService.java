package com.lec.spring.training.service;

import com.lec.spring.base.domain.User;
import com.lec.spring.base.repository.UserRepository;
import com.lec.spring.training.DTO.QryResultDTO;
import com.lec.spring.training.DTO.QryReviewListDTO;
import com.lec.spring.training.domain.Review;
import com.lec.spring.training.repository.ReviewRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;

    public ReviewService(ReviewRepository reviewRepository, UserRepository userRepository) {
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
    }

    // 특정 글의 리뷰 목록
    public QryReviewListDTO list(Long postId) {
        QryReviewListDTO list = new QryReviewListDTO();

        List<Review> reviews = reviewRepository.findByTraining(trainingId, Sort.by(Sort.Order.desc("id")));

        list.setCount(reviews.size());  // 댓글의 개수
        list.setList(reviews);
        list.setStatus("OK");

        return list;
    }

    // 특정 글(postId)에 특정 사용자
    public QryResultDTO write(Long postId, Long userId, String content) {
        User user = userRepository.findById(userId).orElse(null);

        Review review = Review.builder()
                .training(trainingId)
                .rating(rating)
                .content(content)
                .build();

        ReviewRepository.save(review);    // INSERT

        QryResultDTO result = QryResultDTO.builder()
                .count(1)
                .status("OK")
                .build();

        return result;
    }

    public QryResultDTO delete(Long id) {

        Review review = reviewRepository.findById(id).orElse(null);
        int count = 0;
        String status = "FAIL";

        if (review != null) {
            reviewRepository.delete(review);
            count = 1;
            status = "OK";
        }

        QryResultDTO result = QryResultDTO.builder()
                .count(count)
                .status(status)
                .build();

        return result;
    }
}






