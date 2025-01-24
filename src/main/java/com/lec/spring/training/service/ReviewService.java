package com.lec.spring.training.service;

import com.lec.spring.base.domain.User;
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

    // 특정 글의 댓글 목록
    @Override
    public QryReviewList list(Long postId) {
        QryReviewList list = new QryCommentList();

        List<Review> reviews = reviewRepository.findByPost(postId, Sort.by(Sort.Order.desc("id")));

        list.setCount(reviews.size());  // 댓글의 개수
        list.setList(reviews);
        list.setStatus("OK");

        return list;
    }

    // 특정 글(postId)에 특정 사용자
    @Override
    public QryResult write(Long postId, Long userId, String content) {
        User user = userRepository.findById(userId).orElse(null);

        Review review = Review.builder()
                .user(user)
                .post(postId)
                .content(content)
                .build();

        ReviewRepository.save(review);    // INSERT

        QryResult result = QryResult.builder()
                .count(1)
                .status("OK")
                .build();

        return result;
    }

    @Override
    public QryResult delete(Long id) {

        Review review = reviewRepository.findById(id).orElse(null);
        int count = 0;
        String status = "FAIL";

        if (review != null) {
            reviewRepository.delete(review);
            count = 1;
            status = "OK";
        }

        QryResult result = QryResult.builder()
                .count(count)
                .status(status)
                .build();

        return result;
    }
}






