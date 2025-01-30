package com.lec.spring.training.service;

import com.lec.spring.training.DTO.CertificationDTO;
import com.lec.spring.training.DTO.TrainerProfileDTO;
import com.lec.spring.training.domain.GrantStatus;
import com.lec.spring.training.domain.TrainerProfile;
import com.lec.spring.training.repository.TrainerProfileRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class TrainerProfileDTOService {
    private final TrainerProfileRepository trainerProfileRepository;

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

    // TrainerProfile → TrainerProfileDTO 변환 메서드
    private TrainerProfileDTO convertToDTO(TrainerProfile trainerProfile) {
        return TrainerProfileDTO.builder()
                .id(trainerProfile.getId())
                .trainerName(trainerProfile.getTrainer().getUsername())
                .perPrice(trainerProfile.getPerPrice())
                .content(trainerProfile.getContent())
                .career(trainerProfile.getCareer())
                .isAccess(trainerProfile.getIsAccess().name())
                .certifications(trainerProfile.getCertificationList().stream()
                        .map(cert -> CertificationDTO.builder()
                                .skills(cert.getSkills())
                                .imageUrl(cert.getCredentials())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }
}
