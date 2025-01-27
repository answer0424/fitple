package com.lec.spring.training.service;

import com.lec.spring.base.repository.UserRepository;
import com.lec.spring.training.DTO.CreateReservationDTO;
import com.lec.spring.training.DTO.MonthReservationDTO;
import com.lec.spring.training.DTO.StudentListDTO;
import com.lec.spring.training.DTO.TodayReservationDTO;
import com.lec.spring.training.repository.ReservationRepository;
import com.lec.spring.training.repository.TrainingRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MyPageServiceImpl implements MyPageService {

    private final ReservationRepository reservationRepository;
    private final TrainingRepository trainingRepository;
    private final UserRepository userRepository;

    public MyPageServiceImpl(ReservationRepository reservationRepository, TrainingRepository trainingRepository, UserRepository userRepository) {
        this.reservationRepository = reservationRepository;
        this.trainingRepository = trainingRepository;
        this.userRepository = userRepository;
    }


    @Override
    public List<MonthReservationDTO> filterSchedulesByMonth(Long userId, int month) {
        List<MonthReservationDTO> res = new ArrayList<>();
        trainingRepository.findBy


        return res;
    }

    @Override
    public List<TodayReservationDTO> filterSchedulesByDay(Long userId, LocalDate date) {
        return List.of();
    }

    @Override
    public void updateStampStatus(String status, Long reservationId) {

    }

    @Override
    public int showStampList(Long studentId, Long trainerId) {
        return 0;
    }

    @Override
    public void useCoupon(Long studentId, Long trainerId) {

    }

    @Override
    public void changeCouponPageByTrainer(Long studentId, Long trainerId) {

    }

    @Override
    public int getPtCount(Long studentId, Long trainerId) {
        return 0;
    }

    @Override
    public List<StudentListDTO> getMyStudentList(Long trainerId) {
        return List.of();
    }

    @Override
    public StudentListDTO findStudentByChats(Long trainerId, String studentName) {
        return null;
    }

    @Override
    public void addTraining(Long studentId, Long trainerId) {

    }

    @Override
    public void addSchedule(CreateReservationDTO reservationDTO) {

    }

    @Override
    public List<MonthReservationDTO> getSchedulesByMember(Long studentId, Long trainerId) {
        return List.of();
    }

    @Override
    public int findTrainingId(Long studentId, Long trainerId) {
        return trainingRepository.findByUserIdAndTrainerIdEquals(studentId, trainerId);
    }
}
