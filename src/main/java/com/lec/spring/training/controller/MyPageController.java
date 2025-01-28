package com.lec.spring.training.controller;

import com.lec.spring.training.DTO.SkillsDTO;
import com.lec.spring.training.DTO.TrainerProfileDTO;
import com.lec.spring.training.service.TrainerDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @PostMapping("/member/detail")
    public ResponseEntity<Boolean> createTrainerProfile(
            @RequestBody TrainerProfileDTO trainerProfileDTO
            ) {
        if(trainerProfileDTO.getTrainerId()==null  ) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        boolean result = trainerDetailService.createTrainerProfile(trainerProfileDTO);

        if(result){
            return new ResponseEntity<>(true, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // [트레이너 상세페이지 수정]
    @PatchMapping("/member/detail")
    public ResponseEntity<Boolean> updateTrainerProfile(
            @RequestBody TrainerProfileDTO trainerProfileDTO,
            @RequestParam List<SkillsDTO> skils,
            @RequestParam Long[] deletedSkillsId

    ) throws IOException {
        boolean result = trainerDetailService.updateTrainerProfile(trainerProfileDTO, skils, deletedSkillsId);
        if(result){
            return new ResponseEntity<>(true, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // [트레이너 상세페이지 조회]
    @GetMapping("/member/detail")
    public ResponseEntity<TrainerProfileDTO> getTrainerProfile(@RequestParam Long trainerId) {
        TrainerProfileDTO trainerProfileDTO = trainerDetailService.getTrainerProfileById(trainerId);
        if (trainerProfileDTO != null) {
            return new ResponseEntity<>(trainerProfileDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }




}// MyPageController
