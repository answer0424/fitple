package com.lec.spring.training.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentList {
    private Long userId;
    private String nickname;
    private int times;
}
