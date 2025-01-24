package com.lec.spring.training.service;

import com.lec.spring.training.domain.Training;
import com.lec.spring.training.repository.TrainingRepository;

public class TrainingService {

    private final TrainingRepository trainingRepository;

    public TrainingService(TrainingRepository trainingRepository) {
        this.trainingRepository = trainingRepository;
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





}// end TrainingService
