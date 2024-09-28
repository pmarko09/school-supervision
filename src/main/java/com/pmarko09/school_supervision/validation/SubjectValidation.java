package com.pmarko09.school_supervision.validation;

import com.pmarko09.school_supervision.exception.subject.IllegalSubjectDataException;
import com.pmarko09.school_supervision.exception.subject.SubjectNotFoundException;
import com.pmarko09.school_supervision.model.entity.Exam;
import com.pmarko09.school_supervision.model.entity.Student;
import com.pmarko09.school_supervision.model.entity.Subject;
import com.pmarko09.school_supervision.repository.SubjectRepository;

public class SubjectValidation {

    public static void validateSubjectData(Subject subject) {
        if (subject.getName() == null || subject.getName().isEmpty()) {
            throw new IllegalSubjectDataException("Subject name can't be null");
        }
        if (subject.getTeacher() != null) {
            throw new IllegalSubjectDataException("Subject has teacher assigned already.");
        }
    }

    public static Subject subjectExists(SubjectRepository subjectRepository, Long id) {
        return subjectRepository.findById(id)
                .orElseThrow(() -> new SubjectNotFoundException(id));
    }

    public static void thisStudentAlreadyAssigned(Subject subject, Student student) {
        if (subject.getStudents().contains(student)) {
            throw new IllegalSubjectDataException("This student is already assigned to this subject.");
        }
    }

    public static void thisExamAlreadyAssigned(Subject subject, Exam exam) {
        if (subject.getExams().contains(exam)) {
            throw new IllegalSubjectDataException("This exam is already added to the subject.");
        }
    }
}
