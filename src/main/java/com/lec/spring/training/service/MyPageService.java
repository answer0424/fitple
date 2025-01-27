package com.lec.spring.training.service;

import com.lec.spring.training.DTO.CreateReservationDTO;
import com.lec.spring.training.DTO.StudentListDTO;
import com.lec.spring.training.domain.Reservation;

import java.util.Date;
import java.util.List;

public interface MyPageService {
    //- 이 달의 일정 띄우기
    List<Reservation> filterSchedulesByMonth(Long userId, Date date);

    //- 오늘의 일정 띄우기
    List<Reservation> filterSchedulesByDay(Long userId, Date date);

    //- 스탬프 상태 변경
    void updateStampStatus(String status, Long reservationId);

    //- 스탬프 띄우기
    int showStampList();

    //- 쿠폰 사용 기능
    void useCoupon();

    //- 트레이너 별 쿠폰 페이지 변경 기능
    void changeCouponPageByTrainer(Long studentId, Long trainerId);

    //- 남은 pt 횟수 불러오기
    int getPtCount(Long studentId, Long trainerId);

    //- 내 회원 목록 불러오기
    List<StudentListDTO> getMyStudentList(Long trainerId);

    //- 채팅 목록에서 회원 이름 검색
    StudentListDTO findStudentByChats(Long trainerId, String studentName);

    //- 트레이닝에 추가하기
    void addTraining(Long studentId, Long trainerId);

    //- 일정 추가 기능
    void addSchedule(CreateReservationDTO reservationDTO);

    //- 회원별 일정 불러오기
    List<Reservation> getSchedulesByMember();

    //- 트레이닝 id 찾기
    int findTrainingId(Long studentId, Long trainerId);


} //end MyPageService
