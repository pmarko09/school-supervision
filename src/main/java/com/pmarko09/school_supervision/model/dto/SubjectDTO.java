package com.pmarko09.school_supervision.model.dto;

import com.pmarko09.school_supervision.model.entity.Exam;
import com.pmarko09.school_supervision.model.entity.Teacher;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubjectDTO {

    private Long id;
    private String name;
    private Long teacherId;
    private Set<Long> examIds;
    private Set<Long> studentIds;

}
