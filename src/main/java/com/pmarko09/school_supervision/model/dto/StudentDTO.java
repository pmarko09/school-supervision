package com.pmarko09.school_supervision.model.dto;

import com.pmarko09.school_supervision.model.entity.SchoolClass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentDTO {

    private Long id;
    private String firstname;
    private String lastname;
    private Integer cardNumber;
    private String email;
    private Long schoolClassId;
    private Set<Long> subjectIds;

}
