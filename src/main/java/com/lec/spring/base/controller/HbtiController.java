package com.lec.spring.base.controller;

import com.lec.spring.base.domain.HBTI;
import com.lec.spring.base.service.HbtiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/hbti")
@RequiredArgsConstructor
public class HbtiController {

    private final HbtiService hbtiService;
    /**
     * 전체 HBTI 데이터를 반환
     */
    @GetMapping("/data")
    public ResponseEntity<Map<String, Object>> getAllHbtiData() {
        try {
            Map<String, Object> hbtiData = hbtiService.getAllHbtiData();
            return ResponseEntity.ok(hbtiData);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body(Map.of("error", "Failed to load HBTI data."));
        }
    }


    /**
     * 클라이언트로부터 답변을 받아 퍼센트 계산 결과 반환
     */
    @PostMapping("/calculate")
    public ResponseEntity<Object> calculatePercentages(@RequestBody List<Integer> answers) {
        try {
            Map<String, Double> percentages = hbtiService.calculateHbtiPercentages(answers);
            return ResponseEntity.ok(percentages);
        } catch (IllegalArgumentException e) {
            // 오류 메시지를 Object로 감싸서 반환
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }


    /**
     * 클라이언트로부터 답변을 받아 HBTI 결과 저장
     */
    @PostMapping("/save")
    public ResponseEntity<String> saveHbtiResult(
            @RequestParam Long userId,
            @RequestBody List<Integer> answers
    ) {
        try {
            hbtiService.processHbti(userId, answers);
            return ResponseEntity.ok("HBTI 결과 저장 성공");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * 특정 사용자의 HBTI 결과 조회
     */
    @GetMapping("/{userId}")
    public ResponseEntity<HBTI> getHbtiByUserId(@PathVariable Long userId) {
        try {
            HBTI hbti = hbtiService.getHbtiByUserId(userId);
            return ResponseEntity.ok(hbti);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    /**
     * 특정 사용자의 HBTI에 해당하는 상세 정보 반환
     */
    @GetMapping("/{userId}/details")
    public ResponseEntity<Map<String, Object>> getHbtiDetails(@PathVariable Long userId) {
        try {
            Map<String, Object> hbtiDetails = hbtiService.getHbtiDetails(userId);
            return ResponseEntity.ok(hbtiDetails);
        } catch (IllegalArgumentException | IOException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * 특정 HBTI 유형의 데이터를 반환
     */
    @GetMapping("/type/{hbtiType}")
    public ResponseEntity<Map<String, Object>> getHbtiDataByType(@PathVariable String hbtiType) {
        try {
            Map<String, Object> hbtiData = hbtiService.getHbtiDataByType(hbtiType);
            return ResponseEntity.ok(hbtiData);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body(Map.of("error", "Failed to load HBTI data."));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/{userId}/result")
    public ResponseEntity<Map<String, Object>> getHbtiResultWithDetails(@PathVariable Long userId) {
        try {
            // 서비스 호출
            Map<String, Object> result = hbtiService.getHbtiResultWithDetails(userId);
            return ResponseEntity.ok(result);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body(Map.of("error", "JSON 데이터를 로드하는 중 문제가 발생했습니다."));
        }
    }

}// end class
