package com.pmarko09.school_supervision.mapper;

import com.pmarko09.school_supervision.model.dto.ExamResultDTO;
import com.pmarko09.school_supervision.model.entity.Exam;
import com.pmarko09.school_supervision.model.entity.ExamResult;
import com.pmarko09.school_supervision.model.entity.Student;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface ExamResultMapper {

    @Mapping(source = "examResult.id", target = "examResultId")
    @Mapping(source = "student", target = "studentId", qualifiedByName = "mapStudentToStudentId")
    @Mapping(source = "exam", target = "examId", qualifiedByName = "mapExamToExamId")
    ExamResultDTO toDto(ExamResult examResult);

    @Named("mapStudentToStudentId")
    default Long mapStudentToStudentId(Student student) {
        return student != null ? student.getId() : null;
    }

    @Named("mapExamToExamId")
    default Long mapExamToExamId(Exam exam) {
        return exam != null ? exam.getId() : null;
    }
}
