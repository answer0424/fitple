package com.lec.spring.training.service;

import com.lec.spring.training.domain.Certification;
import com.lec.spring.training.domain.TrainerProfile;
import com.lec.spring.training.repository.TrainerProfileRepository;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class TrainerDetailServiceImpl implements TrainerDetailService {

    private final TrainerProfileRepository trainerProfileRepository;

    public TrainerDetailServiceImpl(TrainerProfileRepository trainerProfileRepository) {
        this.trainerProfileRepository = trainerProfileRepository;
    }

    @Override
    public boolean createTrainerProfile(TrainerProfile trainerProfile, List<MultipartFile> files) {
        try {
            if (files != null && !files.isEmpty()) {
                trainerProfile.addCertificationList();
            }
            trainerProfileRepository.save(trainerProfile);

            return true;
        } catch (Exception e) {

            return false;
        }
    }

    @Override
    public void getTrainerProfileByNickname(String nickname) {
        // 유저리포지토리에서 findbynickname => get id
        Long id = 1L;
        trainerProfileRepository.findById(id);

        //return 준우가 만드는거에 맞춰
    }

    @Override
    public boolean updateTrainerProfile(TrainerProfile trainerProfile, List<MultipartFile> files) {

        TrainerProfile profile = trainerProfileRepository.findById(1L) //현재 로그인한 유저 or 마이페이지 url로 확인
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 트레이너 프로필이 존재하지 않습니다."));

        if (trainerProfile.getPerPrice() != null) profile.setPerPrice(trainerProfile.getPerPrice());
        if (trainerProfile.getSkills() != null) profile.setSkills(trainerProfile.getSkills());
        if (trainerProfile.getContent() != null) profile.setContent(trainerProfile.getContent());
        if (trainerProfile.getCareer() != null) profile.setCareer(trainerProfile.getCareer());

        // 이미지 처리 유틸

        return false;
    }
}
