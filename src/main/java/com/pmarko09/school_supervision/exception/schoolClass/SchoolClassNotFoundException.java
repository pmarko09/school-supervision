package com.pmarko09.school_supervision.exception.schoolClass;

public class SchoolClassNotFoundException extends RuntimeException {
    public SchoolClassNotFoundException(Long id) {
        super(String.format("School class with id: %s not found", id));
    }
}
