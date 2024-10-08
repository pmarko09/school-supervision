package com.pmarko09.school_supervision.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentDTO {

    private Long id;
    private String firstname;
    private String lastname;
    private Integer cardNumber;
    private String email;
    private Long schoolClassId;
    private Set<Long> subjectIds;
    private Set<Long> examResultsIds;

}
