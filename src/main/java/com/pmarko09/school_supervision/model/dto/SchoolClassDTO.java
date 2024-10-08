package com.pmarko09.school_supervision.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SchoolClassDTO {

    private Long id;
    private Integer number;
    private String name;
    private Set<Long> studentsIds;

}
