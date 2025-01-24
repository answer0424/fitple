package com.lec.spring.training.service;

import com.lec.spring.base.domain.User;
import com.lec.spring.training.domain.Certification;
import com.lec.spring.training.domain.GrantStatus;
import com.lec.spring.training.domain.TrainerProfile;
import com.lec.spring.training.repository.TrainerProfileRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TrainerDetailServiceTest {

    @Mock
    private TrainerProfileRepository trainerProfileRepository;

    @InjectMocks
    private TrainerDetailService trainerDetailService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
}
