package com.lec.spring.training.DTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReviewResponseDTO {
    private Long id;
    private Long trainingId;
    private String username;
    private String trainerName;
    private String userProfileImage;
    private String trainerProfileImage;
    private Byte rating;
    private String content;
}
