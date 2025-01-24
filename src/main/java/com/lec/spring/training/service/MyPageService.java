package com.lec.spring.training.service;

import com.lec.spring.training.domain.Training;
import com.lec.spring.training.repository.ReservationRepository;
import com.lec.spring.training.domain.Reservation;
import com.lec.spring.training.domain.ReservationStatus;

import com.lec.spring.training.repository.TrainingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class MyPageService {

    private final ReservationRepository reservationRepository;
    private final TrainingRepository trainingRepository;

    public Reservation reservationRepository(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    // # 아이디 별 찾기
    public Optional<Reservation> findById(Long id) {
        return reservationRepository.findById(id);
    }

    // #트레이닝 아이디 조회
    public List<Reservation> findByTrainingId(Long trainingId) {
        return reservationRepository.findByTrainingId(trainingId);
    }

    // # 상태 조회
    public List<Reservation> findByStatus(ReservationStatus status) {
        return reservationRepository.findByStatus(status);
    }

    // # 예약 삭제
    public void deleteReservation(Long id) {
        reservationRepository.deleteById(id);
    }

    // # 상태 업데이트
    public Reservation updateReservationStatus(Long id, ReservationStatus status) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("예약을 찾을 수 없습니다. :  " + id));
        reservation.setStatus(status);
        return reservationRepository.save(reservation);
    }

    // #트레이닝 생성(?)
    public Training save(Training training) {
        if(training.getTimes()== null || training.getTimes() <= 0){
            training.setTimes(0);
        }
        return trainingRepository.save(training);
    }

    // #트레이닝 횟수 업데이트
    public Training updateTime(Long id) {
        return trainingRepository.findById(id).map(training -> {
            training.setTimes(training.getTimes() + 1);
            return trainingRepository.save(training);
        }).orElseThrow(() -> new IllegalArgumentException("pt를 찾을 수 없습니다 : " + id));
    }

    // #트레이닝 횟수감소 업데이트
    public Training decrementTimes(Long id) {
        return trainingRepository.findById(id).map(training -> {
            if (training.getTimes() > 1) {
                training.setTimes(training.getTimes() - 1); // 횟수 1 감소
            } else {
                throw new IllegalStateException("1보다는 작을 수 없다");
            }
            return trainingRepository.save(training);
        }).orElseThrow(() -> new IllegalArgumentException("pt를 찾을 수 없습니다 : " + id));
    }






}// end ReservationService
