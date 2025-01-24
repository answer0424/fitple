package com.lec.spring.training.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QryResultDTO {
    int count;   // 결괏값 (정수)
    String status;   // 처리 결과 메세지
}









