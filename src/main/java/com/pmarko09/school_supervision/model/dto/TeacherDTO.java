package com.pmarko09.school_supervision.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TeacherDTO {

    private Long id;
    private String firstname;
    private String lastname;
    private String email;
    private Long subjectId;

}
