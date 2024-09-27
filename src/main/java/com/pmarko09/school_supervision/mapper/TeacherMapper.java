package com.pmarko09.school_supervision.mapper;

import com.pmarko09.school_supervision.model.dto.TeacherDTO;
import com.pmarko09.school_supervision.model.entity.Subject;
import com.pmarko09.school_supervision.model.entity.Teacher;
import jdk.jfr.Name;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface TeacherMapper {

    @Mapping(source = "subject", target = "subjectId", qualifiedByName = "mapSubjectToSubjectId")
    TeacherDTO toDto(Teacher teacher);

    @Named("mapSubjectToSubjectId")
    default Long mapSubjectToSubjectId(Subject subject) {
        return subject != null ? subject.getId() : null;
    }
}


