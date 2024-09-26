package com.pmarko09.school_supervision.validation;

import com.pmarko09.school_supervision.exception.exam.ExamNotFoundException;
import com.pmarko09.school_supervision.exception.exam.IllegalExamDataException;
import com.pmarko09.school_supervision.model.entity.Exam;
import com.pmarko09.school_supervision.repository.ExamRepository;

public class ExamValidation {

    public static void validateExamData(Exam exam) {
        if (exam.getGrade() == null) {
            throw new IllegalExamDataException("Exam's grade can't be null");
        }
        if (exam.getSubject() == null) {
            throw new IllegalExamDataException("Exam's subject can't be null");
        }
    }

    public static Exam examExists(ExamRepository examRepository, Long id) {
        return examRepository.findById(id)
                .orElseThrow(() -> new ExamNotFoundException(id));
    }
}
