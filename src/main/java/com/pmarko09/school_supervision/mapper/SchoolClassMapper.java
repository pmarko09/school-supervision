package com.pmarko09.school_supervision.mapper;

import com.pmarko09.school_supervision.model.dto.SchoolClassDTO;
import com.pmarko09.school_supervision.model.entity.SchoolClass;
import com.pmarko09.school_supervision.model.entity.Student;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface SchoolClassMapper {

    @Mapping(source = "students", target = "studentsIds", qualifiedByName = "mapStudentsToStudentsIds")
    SchoolClassDTO toDto(SchoolClass schoolClass);

    @Named("mapStudentsToStudentsIds")
    default Set<Long> mapStudentsToStudentsIds(Set<Student> students) {
        return students.stream()
                .map(Student::getId)
                .collect(Collectors.toSet());
    }
}
