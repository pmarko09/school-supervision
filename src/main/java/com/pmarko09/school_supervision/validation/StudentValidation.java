package com.pmarko09.school_supervision.validation;

import com.pmarko09.school_supervision.exception.student.IllegalStudentDataException;
import com.pmarko09.school_supervision.exception.student.StudentNotFoundException;
import com.pmarko09.school_supervision.model.entity.Student;
import com.pmarko09.school_supervision.repository.StudentRepository;

public class StudentValidation {

    public static void validateStudentData(Student student) {
        if (student.getLastname() == null || student.getLastname().isEmpty()) {
            throw new IllegalStudentDataException("Student's name can't be null");
        }
        if (student.getCardNumber() == null) {
            throw new IllegalStudentDataException("Student's card number can't be null");
        }
    }

    public static Student studentExists(StudentRepository studentRepository, Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException(id));
    }
}
