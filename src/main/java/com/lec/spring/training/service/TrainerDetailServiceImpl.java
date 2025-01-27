package com.lec.spring.training.service;

import com.lec.spring.training.DTO.SkillsDTO;
import com.lec.spring.training.DTO.TrainerProfileDTO;
import com.lec.spring.training.domain.Certification;
import com.lec.spring.training.domain.TrainerProfile;
import com.lec.spring.training.repository.CertificationRepository;
import com.lec.spring.training.repository.TrainerProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class TrainerDetailServiceImpl implements TrainerDetailService {

    private final TrainerProfileRepository trainerProfileRepository;
    private final CertificationRepository certificationRepository;
    // UserRepository 필요
    private final ImgService imgService;
    private final  String imgDir ="com/lec/spring/util";

    @Autowired
    public TrainerDetailServiceImpl(TrainerProfileRepository trainerProfileRepository, CertificationRepository certificationRepository, ImgService imgService) {
        this.trainerProfileRepository = trainerProfileRepository;
        this.certificationRepository = certificationRepository;
        this.imgService = imgService;
    }


    // # 트레이너 프로필 생성
    @Override
    @Transactional
    public boolean createTrainerProfile(TrainerProfileDTO trainerProfileDTO, List<SkillsDTO> skills) {
        try {
            TrainerProfile trainerProfile = TrainerProfile.builder()
//                    .trainer(trainerProfileDTO.getTrainerId()) // UserRepository 필요(정보를 가져오는것 DB, DB와 연결 => Repository)
                    .career(trainerProfileDTO.getCareer())
                    .content(trainerProfileDTO.getContent())
                    .perPrice(trainerProfileDTO.getPerPrice())
                    .build();


            trainerProfileRepository.save(trainerProfile);
            if (skills != null && !skills.isEmpty()) {
                for (SkillsDTO file : skills) {
                    Certification certification = new Certification();
                    certification.setTrainerProfile(trainerProfile);
                    trainerProfile.addCertificationList(certification);
                    imgService.saveImage(file.getImg(), imgDir);
                }
            }

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // # 트레이너 닉네임으로 프로필 가져오기
    @Override
    public void getTrainerProfileByNickname(String nickname) {
        // 유저리포지토리에서 findbynickname => get id
        Long id = 1L;
        trainerProfileRepository.findById(id);

        //return 준우가 만드는거에 맞춰
    }

    // 트레이너 프로필 수정
    @Override
    @Transactional
    public boolean updateTrainerProfile(TrainerProfileDTO trainerProfile, List<SkillsDTO> skills, Long[] deletedSkillsId) throws IOException {
        /*
        *  현재 흐름 -> 수정된 것만 가져오는 것
        *   react에서 useState하는 과정에서 별개의 값이 아닌 전체값을 가져오게 될 경우
        *   기존데이터를 삭제하고 새로 넣는과정을 해야함. 현재 코드와 충돌이 일어날 가능성이 높음
        *   기존 useState 새로운 값을 넣는 useState를 따로 만들어서 후자를 백단으로 보내는게 좋을 것 같음.
        * */

        try{
            TrainerProfile profile = trainerProfileRepository.findById(1L) //현재 로그인한 유저 or 마이페이지 url로 확인
                    .orElseThrow(() -> new IllegalArgumentException("해당 ID의 트레이너 프로필이 존재하지 않습니다."));

            if (trainerProfile.getPerPrice() != null) profile.setPerPrice(trainerProfile.getPerPrice());
            if (trainerProfile.getContent() != null) profile.setContent(trainerProfile.getContent());
            if (trainerProfile.getCareer() != null) profile.setCareer(trainerProfile.getCareer());
            if (deletedSkillsId != null && deletedSkillsId.length > 0) {
                // 삭제된 경력이 존재한다면
                for (Long id : deletedSkillsId) {
                    certificationRepository.deleteById(id);
                }
            }
            if (skills != null && !skills.isEmpty()) {
                for (SkillsDTO file : skills) {
                    Certification certification = new Certification();
                    certification.setSkills(file.getSkills());
                    certification.setTrainerProfile(profile);
                    // 명시적으로 Certification 저장
                    certificationRepository.save(certification);
                    profile.addCertificationList(certification);
                }

            }

            trainerProfileRepository.save(profile);
            return true;
        }catch (Exception e){
            return false;
        }
    }


}// end TrainerDetailService
