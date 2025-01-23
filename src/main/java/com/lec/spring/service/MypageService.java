package com.lec.spring.service;

import com.lec.spring.repository.MypageRepository;
import com.lec.spring.training.domain.Reservation;
import com.lec.spring.training.domain.ReservationStatus;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class MypageService {

    private final MypageRepository mypageRepository;

    public Reservation mypageRepository(Reservation reservation) {
        return mypageRepository.save(reservation);
    }

    // # 아이디 별 찾기
    public Optional<Reservation> findById(Long id) {
        return mypageRepository.findById(id);
    }

    // #트레이닝 아이디 조회
    public List<Reservation> findByTrainingId(Long trainingId) {
        return mypageRepository.findByTrainingId(trainingId);
    }

    // # 상태 조회
    public List<Reservation> findByStatus(ReservationStatus status) {
        return mypageRepository.findByStatus(status);
    }

    // # 예약 삭제
    public void deleteReservation(Long id) {
        mypageRepository.deleteById(id);
    }

    // # 상태 업데이트
    public Reservation updateReservationStatus(Long id, ReservationStatus status) {
        Reservation reservation = mypageRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("예약을 찾을 수 없습니다. :  " + id));
        reservation.setStatus(status);
        return mypageRepository.save(reservation);
    }

    // #




}// end MyPageService
