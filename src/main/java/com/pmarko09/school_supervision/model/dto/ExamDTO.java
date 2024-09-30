package com.pmarko09.school_supervision.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExamDTO {

    private Long id;
    private Long subjectId;
    private Long examResultId;
    private LocalDateTime time;

}
