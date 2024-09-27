package com.pmarko09.school_supervision.exception.examResult;

public class ExamResultNotFoundException extends RuntimeException {
    public ExamResultNotFoundException(Long id) {
        super(String.format("Exam result with id: %s not found", id));
    }
}
