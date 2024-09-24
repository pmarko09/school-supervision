package com.pmarko09.school_supervision.model.dto;

import com.pmarko09.school_supervision.model.entity.Subject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExamDTO {

    private Long id;
    private Subject subject;
    private Double grade;
    private LocalDateTime time;

}
