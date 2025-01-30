package com.lec.spring.training.service;

import com.lec.spring.base.domain.Gym;
import com.lec.spring.base.domain.HBTI;
import com.lec.spring.base.repository.GymRepository;
import com.lec.spring.base.repository.HbtiRepository;
import com.lec.spring.training.DTO.CertificationDTO;
import com.lec.spring.training.DTO.TrainerProfileDTO;
import com.lec.spring.training.domain.GrantStatus;
import com.lec.spring.training.domain.TrainerProfile;
import com.lec.spring.training.repository.TrainerProfileRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class TrainerProfileDTOService {
    private final TrainerProfileRepository trainerProfileRepository;
    private final HbtiRepository hbtiRepository;
    private final GymRepository gymRepository;

    // 특정 트레이너 ID로 트레이너 프로필 조회 (DTO 변환)
    public TrainerProfileDTO getTrainerProfileById(Long trainerId) {
        TrainerProfile trainerProfile = trainerProfileRepository.findByTrainerId(trainerId)
                .orElseThrow(() -> new EntityNotFoundException("해당 트레이너 프로필을 찾을 수 없습니다."));

        return convertToDTO(trainerProfile);
    }

    // 승인된 트레이너 목록 조회 (DTO 변환)
    public List<TrainerProfileDTO> getApprovedTrainers() {
        List<TrainerProfile> trainers = trainerProfileRepository.findByIsAccess(GrantStatus.승인);
        return trainers.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    private TrainerProfileDTO convertToDTO(TrainerProfile trainerProfile) {
        // ✅ 트레이너의 사용자 ID 가져오기
        Long userId = trainerProfile.getTrainer().getId();

        // ✅ HBTI 정보 조회 (User ID 기반)
        HBTI hbti = hbtiRepository.findByUserId(userId)
                .orElse(null);

        // ✅ User 엔티티에서 gymId 가져오기
        Long gymId = trainerProfile.getTrainer().getGym() != null
                ? trainerProfile.getTrainer().getGym().getId()
                : null;

        // ✅ Gym 정보 조회
        Gym gym = gymId != null ? gymRepository.findById(gymId).orElse(null) : null;

        return TrainerProfileDTO.builder()
                .id(trainerProfile.getId())
                .trainerName(trainerProfile.getTrainer().getUsername())
                .trainerEmail(trainerProfile.getTrainer().getEmail())
                .perPrice(trainerProfile.getPerPrice())
                .content(trainerProfile.getContent())
                .career(trainerProfile.getCareer()) // ✅ LocalDateTime → LocalDate 변환
                .isAccess(trainerProfile.getIsAccess().name())
                .certifications(trainerProfile.getCertificationList().stream()
                        .map(cert -> CertificationDTO.builder()
                                .skills(cert.getSkills())
                                .imageUrl(cert.getCredentials())
                                .build())
                        .collect(Collectors.toList()))
                // ✅ HBTI 정보 추가
                .hbti(hbti != null ? hbti.getHbti() : "정보 없음")
                // ✅ 체육관 정보 추가
                .gymName(gym != null ? gym.getName() : "체육관 정보 없음")
                .gymAddress(gym != null ? gym.getAddress() : "위치 정보 없음")
                .gymLatitude(gym != null ? gym.getLatitude() : null)
                .gymLongitude(gym != null ? gym.getLongitude() : null)
                .build();
    }

}
