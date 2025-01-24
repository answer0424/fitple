package com.lec.spring.training.service;

import com.lec.spring.base.domain.User;
import com.lec.spring.training.domain.Certification;
import com.lec.spring.training.domain.GrantStatus;
import com.lec.spring.training.domain.TrainerProfile;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface TrainerDetailService {
    TrainerProfile createTrainerProfile(User trainer, Integer perPrice, String skills, String content,
                                        Date career, GrantStatus isAccess, List<Certification> certifications);

    List<TrainerProfile> getAllTrainerProfiles();

    Optional<TrainerProfile> getTrainerProfileById(Long id);

    TrainerProfile updateTrainerProfile(Long id, Integer perPrice, String skills, String content,
                                        Date career, GrantStatus isAccess, List<Certification> certifications);


}// end TrainerDetailService
