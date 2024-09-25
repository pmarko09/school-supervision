package com.pmarko09.school_supervision.mapper;

import com.pmarko09.school_supervision.model.dto.SubjectDTO;
import com.pmarko09.school_supervision.model.entity.Exam;
import com.pmarko09.school_supervision.model.entity.Subject;
import com.pmarko09.school_supervision.repository.SubjectRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface SubjectMapper {

    @Mapping(source = "exams", target = "examIds", qualifiedByName = "mapExamsToExamsIds")
    SubjectDTO toDto(Subject subject);

    @Named("mapExamsToExamsIds")
    default Set<Long> mapExamsToExamsIds(Subject subject) {
        return subject.getExams().stream()
                .map(Exam::getId)
                .collect(Collectors.toSet());
    }
}
