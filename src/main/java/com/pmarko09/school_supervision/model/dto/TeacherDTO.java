package com.pmarko09.school_supervision.model.dto;

import com.pmarko09.school_supervision.model.entity.Subject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeacherDTO {

    private Long id;
    private String firstname;
    private String lastname;
    private String email;
    private Long subjectId;

}
