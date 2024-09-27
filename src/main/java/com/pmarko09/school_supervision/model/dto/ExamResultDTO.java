package com.pmarko09.school_supervision.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExamResultDTO {

    private Long studentId;
    private Long examId;
    private Double grade;
    private LocalDate time;

}
