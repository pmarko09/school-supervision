package com.pmarko09.school_supervision.validation;

import com.pmarko09.school_supervision.exception.teacher.IllegalTeacherDataException;
import com.pmarko09.school_supervision.exception.teacher.TeacherNotFoundException;
import com.pmarko09.school_supervision.model.entity.Teacher;
import com.pmarko09.school_supervision.repository.TeacherRepository;

public class TeacherValidation {

    public static void validateTeacherData(Teacher teacher) {
        if (teacher.getLastname() == null || teacher.getLastname().isEmpty()) {
            throw new IllegalTeacherDataException("Teacher's lastname can't be null");
        }
        if  (teacher.getSubject() != null) {
            throw new IllegalTeacherDataException("Teacher has already assigned the subject.");
        }
    }

    public static Teacher teacherExists(TeacherRepository teacherRepository, Long id) {
        return teacherRepository.findById(id)
                .orElseThrow(() -> new TeacherNotFoundException(id));
    }

}
