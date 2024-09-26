package com.pmarko09.school_supervision.validation;

import com.pmarko09.school_supervision.exception.subject.IllegalSubjectDataException;
import com.pmarko09.school_supervision.exception.subject.SubjectNotFoundException;
import com.pmarko09.school_supervision.model.entity.Subject;
import com.pmarko09.school_supervision.repository.SubjectRepository;

public class SubjectValidation {

    public static void validateSubjectData(Subject subject) {
        if (subject.getName() == null || subject.getName().isEmpty()) {
            throw new IllegalSubjectDataException("Subject name can't be null");
        }
        if (subject.getTeacher() == null) {
            throw new IllegalSubjectDataException("Subject's teacher can't be null");
        }
    }

    public static Subject subjectExists(SubjectRepository subjectRepository, Long id) {
        return subjectRepository.findById(id)
                .orElseThrow(() -> new SubjectNotFoundException(id));
    }
}
