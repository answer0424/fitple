package com.lec.spring.base.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lec.spring.base.domain.HBTI;
import com.lec.spring.base.domain.User;
import com.lec.spring.base.repository.HbtiRepository;
import com.lec.spring.base.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class HbtiService {

    private final HbtiRepository hbtiRepository;
    private final UserRepository userRepository;
    private final Path jsonFilePath;

    /**
     * JSON 데이터 로드
     * @return 전체 HBTI JSON 데이터를 반환
     * @throws IOException JSON 파일을 읽을 수 없을 경우 예외 발생
     */
    private Map<String, Object> loadHbtiData() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String jsonContent = Files.readString(jsonFilePath);
        return mapper.readValue(jsonContent, new TypeReference<Map<String, Object>>() {});
    }

    /**
     * 특정 사용자 HBTI의 상세 정보를 반환
     * @param userId 사용자 ID
     * @return 해당 사용자의 HBTI 세부 정보
     * @throws IOException JSON 데이터 로드 실패 시 예외 발생
     */
    public Map<String, Object> getHbtiDetails(Long userId) throws IOException {
        HBTI hbti = hbtiRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("HBTI 결과를 찾을 수 없습니다. userId: " + userId));
        String userHbti = hbti.getHbti();

        Map<String, Object> hbtiData = loadHbtiData();
        Map<String, Object> details = (Map<String, Object>) hbtiData.get(userHbti);

        if (details == null) {
            throw new IllegalArgumentException("HBTI 데이터가 존재하지 않습니다. HBTI: " + userHbti);
        }

        return details;
    }

    /**
     * 답변을 기반으로 각 성향의 퍼센트를 계산
     * @param answers 사용자가 응답한 답변 리스트 (12개)
     * @return 각 성향의 퍼센트 맵 (M/B, E/I, C/N, P/G)
     */
    public Map<String, Double> calculateHbtiPercentages(List<Integer> answers) {
        if (answers.size() != 12) {
            throw new IllegalArgumentException("12개의 답변이 필요합니다.");
        }

        List<Integer> mScores = answers.subList(0, 3);
        List<Integer> eScores = answers.subList(3, 6);
        List<Integer> cScores = answers.subList(6, 9);
        List<Integer> pScores = answers.subList(9, 12);

        double mPercent = calculateAverage(mScores);
        double ePercent = calculateAverage(eScores);
        double cPercent = calculateAverage(cScores);
        double pPercent = calculateAverage(pScores);

        double bPercent = 100 - mPercent;
        double iPercent = 100 - ePercent;
        double nPercent = 100 - cPercent;
        double gPercent = 100 - pPercent;

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

    /**
     * 퍼센트를 기반으로 HBTI 결과 결정
     * @param percentages 각 성향의 퍼센트 맵
     * @return HBTI 결과 문자열 (예: "MECP")
     */
    public String determineHbti(Map<String, Double> percentages) {
        StringBuilder hbti = new StringBuilder();
        hbti.append(percentages.get("M") >= percentages.get("B") ? "M" : "B");
        hbti.append(percentages.get("E") >= percentages.get("I") ? "E" : "I");
        hbti.append(percentages.get("C") >= percentages.get("N") ? "C" : "N");
        hbti.append(percentages.get("P") >= percentages.get("G") ? "P" : "G");
        return hbti.toString();
    }

    /**
     * 특정 사용자 ID와 답변을 받아 HBTI 결과를 계산하고 저장
     * @param userId 사용자 ID
     * @param answers 사용자의 답변 리스트
     */
    public void processHbti(Long userId, List<Integer> answers) {
        Map<String, Double> percentages = calculateHbtiPercentages(answers);
        String hbti = determineHbti(percentages);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        HBTI hbtiEntity = new HBTI();
        hbtiEntity.setUserId(userId);
        hbtiEntity.setUser(user);
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

    /**
     * 특정 사용자 ID로 HBTI 결과를 조회
     * @param userId 사용자 ID
     * @return HBTI 결과 객체
     */
    public HBTI getHbtiByUserId(Long userId) {
        return hbtiRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("HBTI 결과를 찾을 수 없습니다. userId: " + userId));
    }

    /**
     * 특정 HBTI 유형의 데이터를 반환
     * @param hbtiType HBTI 유형 (예: "MECP")
     * @return HBTI 유형의 상세 데이터
     * @throws IOException JSON 데이터 로드 실패 시 예외 발생
     */
    public Map<String, Object> getHbtiDataByType(String hbtiType) throws IOException {
        Map<String, Object> hbtiData = loadHbtiData();

        if (!hbtiData.containsKey(hbtiType)) {
            throw new IllegalArgumentException("HBTI 유형이 잘못되었습니다: " + hbtiType);
        }

        return (Map<String, Object>) hbtiData.get(hbtiType);
    }

    /**
     * JSON 데이터 전체를 반환
     * @return JSON 데이터의 전체 맵
     * @throws IOException JSON 파일 로드 실패 시 예외 발생
     */
    public Map<String, Object> getAllHbtiData() throws IOException {
        return loadHbtiData();
    }

    /**
     * 특정 사용자 ID로 HBTI 결과, 퍼센트, 세부 정보를 반환
     * @param userId 사용자 ID
     * @return HBTI 결과, 퍼센트, 세부 정보를 포함한 맵
     * @throws IOException JSON 데이터 로드 실패 시 예외 발생
     */
    public Map<String, Object> getHbtiResultWithDetails(Long userId) throws IOException {
        HBTI hbti = getHbtiByUserId(userId);
        Map<String, Double> percentages = new HashMap<>();
        percentages.put("M", hbti.getMbScore());
        percentages.put("B", 100 - hbti.getMbScore());
        percentages.put("E", hbti.getEiScore());
        percentages.put("I", 100 - hbti.getEiScore());
        percentages.put("C", hbti.getCnScore());
        percentages.put("N", 100 - hbti.getCnScore());
        percentages.put("P", hbti.getPgScore());
        percentages.put("G", 100 - hbti.getPgScore());

        Map<String, Object> hbtiDetails = getHbtiDataByType(hbti.getHbti());

        Map<String, Object> result = new HashMap<>();
        result.put("hbtiType", hbti.getHbti());
        result.put("percentages", percentages);
        result.put("details", hbtiDetails);

        return result;
    }
}
