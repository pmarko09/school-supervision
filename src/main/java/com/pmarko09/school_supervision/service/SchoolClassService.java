package com.pmarko09.school_supervision.service;
import com.pmarko09.school_supervision.mapper.SchoolClassMapper;
import com.pmarko09.school_supervision.model.dto.SchoolClassDTO;
import com.pmarko09.school_supervision.model.entity.SchoolClass;
import com.pmarko09.school_supervision.model.entity.Student;
import com.pmarko09.school_supervision.repository.SchoolClassRepository;
import com.pmarko09.school_supervision.repository.StudentRepository;
import com.pmarko09.school_supervision.validation.SchoolClassValidation;
import com.pmarko09.school_supervision.validation.StudentValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SchoolClassService {

    private final SchoolClassRepository schoolClassRepository;
    private final StudentRepository studentRepository;
    private final SchoolClassMapper schoolClassMapper;

    public Set<SchoolClassDTO> getAllSchoolClasses() {
        return schoolClassRepository.findAll().stream()
                .map(schoolClassMapper::toDto)
                .collect(Collectors.toSet());
    }

    public SchoolClassDTO getSchoolClass(Long id) {
        SchoolClass schoolClass = SchoolClassValidation.schoolClassExists(schoolClassRepository, id);
        return schoolClassMapper.toDto(schoolClass);
    }

    public SchoolClassDTO addSchoolClass(SchoolClass schoolClass) {
        SchoolClassValidation.validateSchoolClassData(schoolClass);
        return schoolClassMapper.toDto(schoolClassRepository.save(schoolClass));
    }

    public SchoolClassDTO updateSchoolClass(Long id, SchoolClass updatedSchoolClass) {
        SchoolClass schoolClass = SchoolClassValidation.schoolClassExists(schoolClassRepository, id);
        SchoolClassValidation.validateSchoolClassData(updatedSchoolClass);
        SchoolClass.update(schoolClass, updatedSchoolClass);
        return schoolClassMapper.toDto(schoolClassRepository.save(schoolClass));
    }

    public SchoolClassDTO deleteSchoolClass(Long id) {
        SchoolClass schoolClass = SchoolClassValidation.schoolClassExists(schoolClassRepository, id);
        schoolClassRepository.delete(schoolClass);
        return schoolClassMapper.toDto(schoolClass);
    }

    public SchoolClassDTO assignSchoolClassToStudents(Long schoolClassId, Set<Long> studentsIds) {
        SchoolClass schoolClass = SchoolClassValidation.schoolClassExists(schoolClassRepository, schoolClassId);

        Set<Student> students = studentsIds.stream()
                .map(studentId -> StudentValidation.studentExists(studentRepository, studentId))
                .collect(Collectors.toSet());

        students.forEach(student -> {
            SchoolClassValidation.studentInThisSchoolClass(student, schoolClassId);
            student.setSchoolClass(schoolClass);
        });

        schoolClass.setStudents(students);
        schoolClassRepository.save(schoolClass);

        return schoolClassMapper.toDto(schoolClass);
    }
}
