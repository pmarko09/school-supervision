package com.pmarko09.school_supervision.mapper;

import com.pmarko09.school_supervision.model.dto.StudentDTO;
import com.pmarko09.school_supervision.model.entity.ExamResult;
import com.pmarko09.school_supervision.model.entity.SchoolClass;
import com.pmarko09.school_supervision.model.entity.Student;
import com.pmarko09.school_supervision.model.entity.Subject;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface StudentMapper {

    @Mapping(source = "schoolClass", target = "schoolClassId", qualifiedByName = "mapSchoolClassToSchoolClassId")
    @Mapping(source = "examResults", target = "examResultsIds", qualifiedByName = "mapExamResultsToExamResultsIds")
    @Mapping(source = "subjects", target = "subjectIds", qualifiedByName = "mapSubjectsToIds")
    StudentDTO toDto(Student student);

    @Named("mapSchoolClassToSchoolClassId")
    default Long mapSchoolClassToSchoolClassId(SchoolClass schoolClass) {
        return schoolClass != null ? schoolClass.getId() : null;
    }

    @Named("mapExamResultsToExamResultsIds")
    default Set<Long> mapExamResultsToExamResultsIds(Set<ExamResult> examResults) {
        return examResults.stream()
                .map(ExamResult::getId)
                .collect(Collectors.toSet());
    }

    @Named("mapSubjectsToIds")
    default Set<Long> mapSubjectsToIds(Set<Subject> subjects) {
        return subjects.stream()
                .map(Subject::getId)
                .collect(Collectors.toSet());
    }

}
