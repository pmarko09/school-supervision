package com.pmarko09.school_supervision.exception.student;

public class StudentNotFoundException extends RuntimeException {
    public StudentNotFoundException(Long id) {
        super(String.format("Student with id: %s not found", id));
    }
}
