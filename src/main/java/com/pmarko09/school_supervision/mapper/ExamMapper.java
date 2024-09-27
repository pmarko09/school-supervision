package com.pmarko09.school_supervision.mapper;

import com.pmarko09.school_supervision.model.dto.ExamDTO;
import com.pmarko09.school_supervision.model.entity.Exam;
import com.pmarko09.school_supervision.model.entity.ExamResult;
import com.pmarko09.school_supervision.model.entity.Subject;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface ExamMapper {

    @Mapping(source = "subject", target = "subjectId", qualifiedByName = "mapSubjectToSubjectId")
    @Mapping(source = "examResult", target = "examResultId", qualifiedByName = "mapExamResultToExamResultId")
    ExamDTO toDto(Exam exam);

    @Named("mapSubjectToSubjectId")
    default Long mapSubjectToSubjectId(Subject subject) {
        return subject != null ? subject.getId() : null;
    }

    @Named("mapExamResultToExamResultId")
    default Long mapExamResultToExamResultId(ExamResult examResult) {
        return examResult != null ? examResult.getId() : null;
    }
}
