package com.lec.spring.training.service;

import com.lec.spring.base.domain.User;
import com.lec.spring.training.domain.Certification;
import com.lec.spring.training.domain.GrantStatus;
import com.lec.spring.training.domain.TrainerProfile;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface TrainerDetailService {
    // 트레이너 프로필 생성
    boolean createTrainerProfile(TrainerProfile trainerProfile, List<MultipartFile> files);
    
//    List<TrainerProfile> getAllTrainerProfiles();

    // 닉네임 기준 프로필 검색/상세 페이지 완성 시 수정
    void getTrainerProfileByNickname(String nickname);

    // 트레이너 프로필 수정
    boolean updateTrainerProfile(TrainerProfile trainerProfile, List<MultipartFile> files);


}// end TrainerDetailService
