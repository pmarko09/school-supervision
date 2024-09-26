package com.pmarko09.school_supervision.validation;

import com.pmarko09.school_supervision.exception.exam.IllegalExamDataException;
import com.pmarko09.school_supervision.exception.schoolClass.IllegalSchoolClassDataException;
import com.pmarko09.school_supervision.exception.schoolClass.SchoolClassNotFoundException;
import com.pmarko09.school_supervision.model.entity.SchoolClass;
import com.pmarko09.school_supervision.repository.SchoolClassRepository;

public class SchoolClassValidation {

    public static void validateSchoolClassData(SchoolClass schoolClass) {
        if (schoolClass.getStudents().isEmpty()) {
            throw new IllegalSchoolClassDataException("School class can't have no students.");
        }
        if (schoolClass.getName() == null || schoolClass.getName().isEmpty()) {
            throw new IllegalSchoolClassDataException("School class can't have no name");
        }
    }

    public static SchoolClass schoolClassExists(SchoolClassRepository schoolClassRepository, Long id) {
        return schoolClassRepository.findById(id)
                .orElseThrow(() -> new SchoolClassNotFoundException(id));
    }
}
