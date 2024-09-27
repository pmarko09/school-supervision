package com.pmarko09.school_supervision.service;

import com.pmarko09.school_supervision.mapper.ExamResultMapper;
import com.pmarko09.school_supervision.model.dto.ExamResultDTO;
import com.pmarko09.school_supervision.model.entity.Exam;
import com.pmarko09.school_supervision.model.entity.ExamResult;
import com.pmarko09.school_supervision.model.entity.Student;
import com.pmarko09.school_supervision.repository.ExamRepository;
import com.pmarko09.school_supervision.repository.ExamResultRepository;
import com.pmarko09.school_supervision.repository.StudentRepository;
import com.pmarko09.school_supervision.validation.ExamResultValidation;
import com.pmarko09.school_supervision.validation.ExamValidation;
import com.pmarko09.school_supervision.validation.StudentValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExamResultService {

    private final ExamResultRepository examResultRepository;
    private final StudentRepository studentRepository;
    private final ExamRepository examRepository;
    private final ExamResultMapper examResultMapper;

    public Set<ExamResultDTO> getAllExamResults() {
        return examResultRepository.findAll().stream()
                .map(examResultMapper::toDto)
                .collect(Collectors.toSet());
    }

    public ExamResultDTO getExamResult(Long id) {
        ExamResult examResult = ExamResultValidation.examResultExists(examResultRepository, id);
        return examResultMapper.toDto(examResult);
    }

    public ExamResultDTO addExamResult(ExamResult examResult) {
        ExamResultValidation.validateExamResultData(examResult);
        return examResultMapper.toDto(examResultRepository.save(examResult));
    }

    public ExamResultDTO updateExamResult(Long id, ExamResult updatedExamResult) {
        ExamResult examResult = ExamResultValidation.examResultExists(examResultRepository, id);
        ExamResultValidation.validateExamResultData(updatedExamResult);
        ExamResult.update(examResult, updatedExamResult);
        return examResultMapper.toDto(examResultRepository.save(examResult));
    }

    public ExamResultDTO deleteExamResult(Long id) {
        ExamResult examResult = ExamResultValidation.examResultExists(examResultRepository, id);
        examResultRepository.delete(examResult);
        return examResultMapper.toDto(examResult);
    }

    public ExamResultDTO assignExamResultToStudent(Long examResultId, Long studentId) {
        ExamResult examResult = ExamResultValidation.examResultExists(examResultRepository, examResultId);
        Student student = StudentValidation.studentExists(studentRepository, studentId);

        examResult.setStudent(student);
        studentRepository.save(student);
        return examResultMapper.toDto(examResultRepository.save(examResult));
    }

    public ExamResultDTO assignExamResultToExam(Long examResultId, Long examId) {
        ExamResult examResult = ExamResultValidation.examResultExists(examResultRepository, examResultId);
        Exam exam = ExamValidation.examExists(examRepository, examId);

        examResult.setExam(exam);
        exam.setExamResult(examResult);

        examRepository.save(exam);

        return examResultMapper.toDto(examResultRepository.save(examResult));
    }
}
