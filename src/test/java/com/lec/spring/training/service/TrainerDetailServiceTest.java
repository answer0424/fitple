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

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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

    // # 트레이너 생성메소더
    @Test
    void createTrainerProfileTest() {
        User trainer = new User(); // Mock User instance
        trainer.setUsername("trainer");
        trainer.setPassword("password");
        trainer.setEmail("trainer@gmail.com");
        trainer.setAddress("주소");
        Integer perPrice = 50000;
        String skills = "체육지도사 2급";
        String content = "열심히 하겠습니다.";
        Date career = new Date();
        GrantStatus isAccess = GrantStatus.APPROVED;
        List<Certification> certifications = Arrays.asList(new Certification());

        TrainerProfile savedProfile = TrainerProfile.builder()
                .trainer(trainer)
                .perPrice(perPrice)
                .skills(skills)
                .content(content)
                .career(career)
                .isAccess(isAccess)
                .build();

        System.out.println(savedProfile);

        when(trainerProfileRepository.save(any(TrainerProfile.class))).thenReturn(savedProfile);

        TrainerProfile result = trainerDetailService.createTrainerProfile(trainer, perPrice, skills, content, career, isAccess, certifications);

        assertNotNull(result);
        assertEquals(perPrice, result.getPerPrice());
        assertEquals(skills, result.getSkills());
        assertEquals(content, result.getContent());
       assertEquals(isAccess, result.getIsAccess());
        verify(trainerProfileRepository, times(1)).save(any(TrainerProfile.class));
    }

    // # 모든 트레이너 프로필 가져오기
    @Test
    void getAllTrainerProfilesTest() {
        List<TrainerProfile> mockProfiles = Arrays.asList(new TrainerProfile(), new TrainerProfile());

        when(trainerProfileRepository.findAll()).thenReturn(mockProfiles);

        List<TrainerProfile> result = trainerDetailService.getAllTrainerProfiles();
        System.out.println(result);

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(trainerProfileRepository, times(1)).findAll();
    }

    // 특정 아이디로 트레이너 조회하기
    @Test
    void getTrainerProfileByIdTest() {
        Long id = 1L;
        TrainerProfile mockProfile = new TrainerProfile();

        when(trainerProfileRepository.findById(id)).thenReturn(Optional.of(mockProfile));

        Optional<TrainerProfile> result = trainerDetailService.getTrainerProfileById(id);
        System.out.println(result);

        assertTrue(result.isPresent());
        assertEquals(mockProfile, result.get());
        verify(trainerProfileRepository, times(1)).findById(id);
    }


    //트레이너 수정메소드
    @Test
    void updateTrainerProfileTest() {
        Long id = 1L;
        Integer perPrice = 60000;
        String skills = "Python, Django";
        String content = "Updated content";
        Date career = new Date();
        GrantStatus isAccess = GrantStatus.PENDING;
        List<Certification> certifications = Arrays.asList(new Certification());

        TrainerProfile existingProfile = TrainerProfile.builder()
                .id(id)
                .perPrice(50000)
                .skills("체육지도사 1급")
                .content("가나다라마바")
                .career(new Date())
                .isAccess(GrantStatus.APPROVED)
                .build();

        System.out.println(existingProfile);

        when(trainerProfileRepository.findById(id)).thenReturn(Optional.of(existingProfile));
        when(trainerProfileRepository.save(any(TrainerProfile.class))).thenReturn(existingProfile);

        TrainerProfile result = trainerDetailService.updateTrainerProfile(id, perPrice, skills, content, career, isAccess, certifications);

        assertNotNull(result);
        assertEquals(perPrice, result.getPerPrice());
        assertEquals(skills, result.getSkills());
        assertEquals(content, result.getContent());
        assertEquals(isAccess, result.getIsAccess());
        verify(trainerProfileRepository, times(1)).findById(id);
        verify(trainerProfileRepository, times(1)).save(existingProfile);
    }

    // 존재하지 않는 트레이너
    @Test
    void updateTrainerProfileNotFoundTest() {
        Long id = 1L;

        when(trainerProfileRepository.findById(id)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            trainerDetailService.updateTrainerProfile(id, null, null, null, null, null, null);
        });
        System.out.println(exception.getMessage());

        assertEquals("해당 ID의 트레이너 프로필이 존재하지 않습니다.", exception.getMessage());
        verify(trainerProfileRepository, times(1)).findById(id);
        verify(trainerProfileRepository, times(0)).save(any(TrainerProfile.class));
    }
}
