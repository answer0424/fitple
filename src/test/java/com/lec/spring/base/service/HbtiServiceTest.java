package com.lec.spring.base.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class HbtiServiceTest {
    @Autowired
    private HbtiService hbtiService;

    @Test
    public void testCalculateHbtiPercentages() {
        List<Integer> answers = Arrays.asList(40, 40, 40, 60, 60, 60, 80, 80, 80, 20, 20, 20);

        Map<String, Double> result = hbtiService.calculateHbtiPercentages(answers);

        Assertions.assertEquals(40.0, result.get("M"));
        Assertions.assertEquals(60.0, result.get("B"));
        Assertions.assertEquals(60.0, result.get("E"));
        Assertions.assertEquals(40.0, result.get("I"));
        Assertions.assertEquals(80.0, result.get("C"));
        Assertions.assertEquals(20.0, result.get("N"));
        Assertions.assertEquals(20.0, result.get("P"));
        Assertions.assertEquals(80.0, result.get("G"));
    }

}