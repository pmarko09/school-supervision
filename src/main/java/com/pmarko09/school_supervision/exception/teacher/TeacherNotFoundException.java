package com.pmarko09.school_supervision.exception.teacher;

public class TeacherNotFoundException extends RuntimeException {
    public TeacherNotFoundException(Long id) {
        super(String.format("Teacher with id: %s not found", id));
    }
}
