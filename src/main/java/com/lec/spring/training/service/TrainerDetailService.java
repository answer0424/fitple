package com.lec.spring.training.service;

import com.lec.spring.base.domain.User;
import com.lec.spring.training.domain.Certification;
import com.lec.spring.training.domain.GrantStatus;
import com.lec.spring.training.domain.TrainerProfile;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface TrainerDetailService {
    // 트레이너 프로필 생성
    TrainerProfile createTrainerProfile();
    
//    List<TrainerProfile> getAllTrainerProfiles();

    // 닉네임 기준 프로필 검색
    TrainerProfile getTrainerProfileByNickname(String nickname);

    // 트레이너 프로필 수정
    TrainerProfile updateTrainerProfile();


}// end TrainerDetailService
