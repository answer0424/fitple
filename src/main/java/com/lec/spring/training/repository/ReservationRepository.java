package com.lec.spring.training.repository;

import com.lec.spring.training.DTO.MonthReservationDTO;
import com.lec.spring.training.domain.Reservation;
import com.lec.spring.training.domain.ReservationStatus;
import com.lec.spring.training.domain.Training;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByTrainingId(Long trainingId);

    List<Reservation> findByStatus(ReservationStatus status);



}// end ReservationRepository
