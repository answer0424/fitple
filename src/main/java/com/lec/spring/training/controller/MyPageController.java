package com.lec.spring.training.controller;

import com.lec.spring.base.config.PrincipalDetails;
import com.lec.spring.training.DTO.SkillsDTO;
import com.lec.spring.training.DTO.TrainerProfileDTO;
import com.lec.spring.training.service.TrainerDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class MyPageController{

    private final TrainerDetailService trainerDetailService;

    //- 내 일정 띄우기


    //- 오늘의 일정 띄우기


    //- 스탬프 상태 변경

    
    //- 스탬프 띄우기


    //- 쿠폰 사용 기능


    //- 트레이너 별 쿠폰 페이지 변경 기능


    //- 남은 pt 횟수 불러오기


    //- 내 회원 목록 불러오기


    //- 채팅 목록에서 회원 이름 검색


    //- 트레이닝에 추가하기


    //- 일정 추가 기능


    //- 회원 일정 불러오기


    // [트레이너 상세페이지 작성]
    /*메소드와 메소드 사이에 정보를 보낼 때는 매개변수로 보내는 것을 잊지말자.!*/
    @PostMapping("/member/detail")
    public ResponseEntity<Boolean> createTrainerProfile(
            @RequestBody TrainerProfileDTO trainerProfileDTO, @AuthenticationPrincipal PrincipalDetails user,
            @RequestParam("skills") List<String> skills, @RequestParam("img") List<MultipartFile> image
            ) {
        if(trainerProfileDTO.getTrainerId()==null  ) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        boolean result = trainerDetailService.createTrainerProfile(trainerProfileDTO, user, skills, image);

        if(result){
            return new ResponseEntity<>(true, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // [트레이너 상세페이지 수정]
    @PatchMapping("/member/detail")
    public ResponseEntity<Boolean> updateTrainerProfile(
            @RequestBody TrainerProfileDTO trainerProfileDTO

    ) throws IOException {
        boolean result = trainerDetailService.updateTrainerProfile(trainerProfileDTO);
        if(result){
            return new ResponseEntity<>(true, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }




}// MyPageController
