package com.pmarko09.school_supervision.validation;

import com.pmarko09.school_supervision.exception.examResult.ExamResultNotFoundException;
import com.pmarko09.school_supervision.exception.examResult.IllegalExamResultDataException;
import com.pmarko09.school_supervision.model.entity.ExamResult;
import com.pmarko09.school_supervision.model.entity.Student;
import com.pmarko09.school_supervision.repository.ExamResultRepository;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ExamResultValidation {

    public static void validateExamResultData(ExamResult examResult) {
        if (examResult.getGrade() == null) {
            throw new IllegalExamResultDataException("Exam result must have grade");
        }
        if (examResult.getExam() != null) {
            throw new IllegalExamResultDataException("Exam result is already assigned to the exam.");
        }
        if (examResult.getStudent() != null) {
            throw new IllegalExamResultDataException("This exam result is already assigned to the student.");
        }
    }

    public static ExamResult examResultExists(ExamResultRepository examResultRepository, Long id) {
        return examResultRepository.findById(id)
                .orElseThrow(() -> new ExamResultNotFoundException(id));
    }

    public static void thisExamResultHasStudentAlready(ExamResult examResult, Student student) {
        if (examResult.getStudent() != null && examResult.getStudent().equals(student)) {
            throw new IllegalExamResultDataException("This exam result is already assigned to this student.");
        }
    }
}
