package com.pmarko09.school_supervision.exception.subject;

public class SubjectNotFoundException extends RuntimeException {
    public SubjectNotFoundException(Long id) {
        super(String.format("Subject with id: %s not found", id));
    }
}
