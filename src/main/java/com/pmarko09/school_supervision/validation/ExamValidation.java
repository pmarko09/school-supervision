package com.pmarko09.school_supervision.validation;

import com.pmarko09.school_supervision.exception.exam.ExamNotFoundException;
import com.pmarko09.school_supervision.exception.exam.IllegalExamDataException;
import com.pmarko09.school_supervision.model.entity.Exam;
import com.pmarko09.school_supervision.model.entity.Subject;
import com.pmarko09.school_supervision.repository.ExamRepository;

public class ExamValidation {

    public static void validateExamData(Exam exam) {
        if (exam.getSubject() != null) {
            throw new IllegalExamDataException("Exam has already assigned the subject.");
        }
        if (exam.getExamResult() != null) {
            throw new IllegalExamDataException("Exam has already assigned exam result.");
        }
    }

    public static Exam examExists(ExamRepository examRepository, Long id) {
        return examRepository.findById(id)
                .orElseThrow(() -> new ExamNotFoundException(id));
    }

    public static void thisExamHasSubject(Subject subject, Exam exam) {
        if (subject.getExams().contains(exam)) {
            throw new IllegalExamDataException("This exam has already assigned subject");
        }
    }
}
