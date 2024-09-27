package com.pmarko09.school_supervision.mapper;

import com.pmarko09.school_supervision.model.dto.SubjectDTO;
import com.pmarko09.school_supervision.model.entity.Exam;
import com.pmarko09.school_supervision.model.entity.Student;
import com.pmarko09.school_supervision.model.entity.Subject;
import com.pmarko09.school_supervision.model.entity.Teacher;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface SubjectMapper {

    @Mapping(source = "teacher", target = "teacherId", qualifiedByName = "mapTeacherToTeacherId")
    @Mapping(source = "students", target = "studentIds", qualifiedByName = "mapStudentsToStudentIds")
    @Mapping(source = "exams", target = "examIds", qualifiedByName = "mapExamsToExamsIds")
    SubjectDTO toDto(Subject subject);

    @Named("mapTeacherToTeacherId")
    default Long mapTeacherToTeacherId(Teacher teacher) {
        return teacher != null ? teacher.getId() : null;
    }

    @Named("mapStudentsToStudentIds")
    default Set<Long> mapStudentsToStudentIds(Set<Student> students) {
        return students.stream()
                .map(Student::getId)
                .collect(Collectors.toSet());
    }

    @Named("mapExamsToExamsIds")
    default Set<Long> mapExamsToExamsIds(Set<Exam> exams) {
        return exams.stream()
                .map(Exam::getId)
                .collect(Collectors.toSet());
    }
}
