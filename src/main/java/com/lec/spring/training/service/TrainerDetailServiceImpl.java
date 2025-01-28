package com.lec.spring.training.service;

import com.lec.spring.base.config.PrincipalDetails;
import com.lec.spring.base.domain.User;
import com.lec.spring.base.repository.UserRepository;
import com.lec.spring.training.DTO.SkillsDTO;
import com.lec.spring.training.DTO.TrainerProfileDTO;
import com.lec.spring.training.controller.MyPageController;
import com.lec.spring.training.domain.Certification;
import com.lec.spring.training.domain.TrainerProfile;
import com.lec.spring.training.repository.CertificationRepository;
import com.lec.spring.training.repository.TrainerProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.lec.spring.training.domain.GrantStatus.승인;

@Service
public class TrainerDetailServiceImpl implements TrainerDetailService {

    private final TrainerProfileRepository trainerProfileRepository;
    private final CertificationRepository certificationRepository;
    // UserRepository 필요
    private final ImgService imgService;
    private final  String imgDir ="com/lec/spring/util";
    private final UserRepository userRepository;

    @Autowired
    public TrainerDetailServiceImpl(TrainerProfileRepository trainerProfileRepository, CertificationRepository certificationRepository, ImgService imgService, UserRepository userRepository) {
        this.trainerProfileRepository = trainerProfileRepository;
        this.certificationRepository = certificationRepository;
        this.imgService = imgService;
        this.userRepository = userRepository;
    }


    // # 트레이너 프로필 생성
    @Transactional
    @Override
    public boolean createTrainerProfile(TrainerProfileDTO trainerProfileDTO, PrincipalDetails user, List<String> skills, List<MultipartFile> images) {
        try {

//            User trainer  = user.getUser();

            User trainer = new User();
            trainer.setId(1L);
            trainer.setNickname("지윤");
            trainer.setUsername("wldbs");
            trainer.setEmail("johndoe@example.com"); // 임시 이메일
            trainer.setPassword("123456");
            TrainerProfile trainerProfile = TrainerProfile.builder()
                    .trainer(trainer) // UserRepository 필요(정보를 가져오는것 DB, DB와 연결 => Repository)
                    .career(trainerProfileDTO.getCareer())
                    .content(trainerProfileDTO.getContent())
                    .perPrice(trainerProfileDTO.getPerPrice())
                    .isAccess(승인) // 원래는 없어야 하는 것이 맞음
                    .build();
//            System.out.println("TrainerProfile : " + trainerProfile);
            trainerProfileRepository.save(trainerProfile);// 프로필 저장후 skills 저장해야함.

                // 여러 개의 스킬과 이미지가 있을 때 처리

            // 이미지 저장을 imgService에서 다루기
            for (int i = 0; i < skills.size() ; i++) {

                    String skill = skills.get(i);
                    MultipartFile image = images.get(i);

                    // 이미지 파일 저장 경로 설정
                    String uploadDir = "./uploads/";
                    File uploadDirFile = new File(uploadDir);
                    if (!uploadDirFile.exists()) {
                        uploadDirFile.mkdirs();  // 디렉토리가 없으면 생성
                    }

                    // 파일 저장
                    String filePath = uploadDir + image.getOriginalFilename();
                    image.transferTo(new File(filePath));

                    // 이곳에 데이터베이스 저장 등 추가적인 작업 가능

                }




                return true;
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
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
    public boolean updateTrainerProfile(TrainerProfileDTO trainerProfile) throws IOException {
        /*
        *  현재 흐름 -> 수정된 것만 가져오는 것
        *   react에서 useState하는 과정에서 별개의 값이 아닌 전체값을 가져오게 될 경우
        *   기존데이터를 삭제하고 새로 넣는과정을 해야함. 현재 코드와 충돌이 일어날 가능성이 높음
        *   기존 useState 새로운 값을 넣는 useState를 따로 만들어서 후자를 백단으로 보내는게 좋을 것 같음.
        * */

        try{
            TrainerProfile profile = trainerProfileRepository.findById(trainerProfile.getTrainerId()) //현재 로그인한 유저 or 마이페이지 url로 확인
                    .orElseThrow(() -> new IllegalArgumentException("해당 ID의 트레이너 프로필이 존재하지 않습니다."));

            if (trainerProfile.getPerPrice() != null) profile.setPerPrice(trainerProfile.getPerPrice());
            if (trainerProfile.getContent() != null) profile.setContent(trainerProfile.getContent());
            if (trainerProfile.getCareer() != null) profile.setCareer(trainerProfile.getCareer());
            if (trainerProfile.getDeletedSkillsId() != null && trainerProfile.getDeletedSkillsId() .length > 0) {
                // 삭제된 경력이 존재한다면
                for (Long id : trainerProfile.getDeletedSkillsId() ) {
                    certificationRepository.deleteById(id);
                }
            }
            if (trainerProfile.getSkills() != null && !trainerProfile.getSkills() .isEmpty()) {
                for (SkillsDTO file : trainerProfile.getSkills() ) {
                    Certification certification = new Certification();
                    certification.setSkills(file.getSkills() != null ? file.getSkills() : "");
                    certification.setTrainerProfile(profile);
                    // 명시적으로 Certification 저장
                    certificationRepository.save(certification);
                    profile.addCertificationList(certification);
                }

            }

            trainerProfileRepository.save(profile);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }



}// end TrainerDetailService

/*
*TODO
* 설계!! 를 생각하면서 코드 짜기!
* imgService 중첩적인 것 합치기 (어떤 차이가 있는지 공부하기!)
* img 데이터 베이스에 저장하는 코드 작성하기
* 리뷰서비스 가져와서 잘 출력되는지 확인하기
* 오후 8시 30분 -> 안된다고 하면 내일
* */