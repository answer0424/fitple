package com.lec.spring.training.service;

import com.lec.spring.training.DTO.TrainerProfileDTO;

import java.io.IOException;

public interface TrainerDetailService {
    // 트레이너 프로필 생성
    boolean createTrainerProfile(TrainerProfileDTO trainerProfile);

//    List<TrainerProfile> getAllTrainerProfiles();

    // 닉네임 기준 프로필 검색/상세 페이지 완성 시 수정
    void getTrainerProfileByNickname(String nickname);

    // 트레이너 프로필 수정
    boolean updateTrainerProfile(TrainerProfileDTO trainerProfile) throws IOException;



}// end TrainerDetailService
