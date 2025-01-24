package com.lec.spring.base.service;

import com.lec.spring.base.domain.HBTI;
import com.lec.spring.base.domain.User;
import com.lec.spring.base.repository.HbtiRepository;
import com.lec.spring.base.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

@SpringBootTest
@Transactional
public class HbtiServiceTest {

    @Autowired
    private HbtiService hbtiService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HbtiRepository hbtiRepository;

    @Test
    void saveHbtiResultTest() {
        //  User 엔티티 초기화
        User user = User.builder()
                .username("testuser")
                .password("testpassword123")
                .email("testuser@example.com")
                .nickname("TestUserNick")
                .authority("ROLE_USER")
                .profileImage("default.png")
                .build();
        userRepository.save(user); // DB에 저장

        // 답변 리스트 초기화
        List<Integer> answers = Arrays.asList(20, 20, 20, 30, 30, 30, 30, 30, 30, 46, 46, 46);

        //  HBTI 계산 및 저장
        hbtiService.processHbti(user.getId(), answers);

        // 저장된 HBTI 결과 검증
        HBTI result = hbtiRepository.findById(user.getId())
                .orElseThrow(() -> new IllegalArgumentException("HBTI 결과를 찾을 수 없습니다."));

        Assertions.assertEquals("BING", result.getHbti());
        Assertions.assertEquals(20.0, result.getMbScore());
        Assertions.assertEquals(30.0, result.getEiScore());
        Assertions.assertEquals(30.0, result.getCnScore());
        Assertions.assertEquals(46.0, result.getPgScore());
    }

    @Test
    void testCalculateHbtiPercentages() {

        List<Integer> answers = Arrays.asList(40, 40, 40, 60, 60, 60, 80, 80, 80, 20, 20, 20);

        //  퍼센트 계산
        Map<String, Double> result = hbtiService.calculateHbtiPercentages(answers);


        String hbti = hbtiService.determineHbti(result);

        // Then: 계산 결과 검증
        Assertions.assertEquals(40.0, result.get("M"));
        Assertions.assertEquals(60.0, result.get("B"));
        Assertions.assertEquals(60.0, result.get("E"));
        Assertions.assertEquals(40.0, result.get("I"));
        Assertions.assertEquals(80.0, result.get("C"));
        Assertions.assertEquals(20.0, result.get("N"));
        Assertions.assertEquals(20.0, result.get("P"));
        Assertions.assertEquals(80.0, result.get("G"));

        // HBTI 출력
        System.out.println("Calculated HBTI: 💩💩💩💩💩💩💩💩💩💩💩💩💩💩💩" + hbti);

        System.out.println("Percentages:");
        result.forEach((key, value) -> System.out.println(key + ": " + value + "%"));
    }







}
