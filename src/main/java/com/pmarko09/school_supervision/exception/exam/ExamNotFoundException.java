package com.pmarko09.school_supervision.exception.exam;

public class ExamNotFoundException extends RuntimeException {
    public ExamNotFoundException(Long id) {
        super(String.format("Exam with id: %s not found.", id));
    }
}
