package com.lec.spring.base.service;

import com.lec.spring.base.domain.HBTI;
import com.lec.spring.base.domain.User;
import com.lec.spring.base.repository.HbtiRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class HbtiService {

    private final HbtiRepository hbtiRepository;
//    private final UserRepository userRepository;

    // 퍼센트 계산
    public Map<String, Double> calculateHbtiPercentages(List<Integer> answers) {
        if (answers.size() != 12) {
            throw new IllegalArgumentException("12개의 답변이 필요합니다.");
        }

        // 질문 그룹별 점수
        List<Integer> mScores = answers.subList(0, 3); // M 관련 질문
        List<Integer> eScores = answers.subList(3, 6); // E 관련 질문
        List<Integer> cScores = answers.subList(6, 9); // C 관련 질문
        List<Integer> pScores = answers.subList(9, 12); // P 관련 질문

        // 각 성향의 평균 계산
        double mPercent = calculateAverage(mScores);
        double ePercent = calculateAverage(eScores);
        double cPercent = calculateAverage(cScores);
        double pPercent = calculateAverage(pScores);

        // 반대 성향 계산
        double bPercent = 100 - mPercent;
        double iPercent = 100 - ePercent;
        double nPercent = 100 - cPercent;
        double gPercent = 100 - pPercent;

        // 결과 맵으로 반환
        Map<String, Double> percentages = new HashMap<>();
        percentages.put("M", mPercent);
        percentages.put("B", bPercent);
        percentages.put("E", ePercent);
        percentages.put("I", iPercent);
        percentages.put("C", cPercent);
        percentages.put("N", nPercent);
        percentages.put("P", pPercent);
        percentages.put("G", gPercent);

        return percentages;
    }

    // HBTI 결과 결정
    public String determineHbti(Map<String, Double> percentages) {
        StringBuilder hbti = new StringBuilder();
        hbti.append(percentages.get("M") >= percentages.get("B") ? "M" : "B");
        hbti.append(percentages.get("E") >= percentages.get("I") ? "E" : "I");
        hbti.append(percentages.get("C") >= percentages.get("N") ? "C" : "N");
        hbti.append(percentages.get("P") >= percentages.get("G") ? "P" : "G");
        return hbti.toString();
    }

    // 전체 로직 실행 및 저장
    public void processHbti(Long userId, List<Integer> answers) {
        // 퍼센트 계산
        Map<String, Double> percentages = calculateHbtiPercentages(answers);

        // HBTI 결과 결정
        String hbti = determineHbti(percentages);

        // 유저 정보 조회
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // DB 저장
        HBTI hbtiEntity = new HBTI();
//        hbtiEntity.setUserId(userId);
//        hbtiEntity.setUser(user);
        hbtiEntity.setMbScore(percentages.get("M"));
        hbtiEntity.setEiScore(percentages.get("E"));
        hbtiEntity.setCnScore(percentages.get("C"));
        hbtiEntity.setPgScore(percentages.get("P"));
        hbtiEntity.setHbti(hbti);

        hbtiRepository.save(hbtiEntity);
    }

    private double calculateAverage(List<Integer> scores) {
        return scores.stream().mapToDouble(Integer::doubleValue).average().orElse(0);
    }
}
