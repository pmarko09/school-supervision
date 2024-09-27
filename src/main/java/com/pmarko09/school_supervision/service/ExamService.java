package com.pmarko09.school_supervision.service;

import com.pmarko09.school_supervision.mapper.ExamMapper;
import com.pmarko09.school_supervision.model.dto.ExamDTO;
import com.pmarko09.school_supervision.model.entity.Exam;
import com.pmarko09.school_supervision.model.entity.ExamResult;
import com.pmarko09.school_supervision.model.entity.Subject;
import com.pmarko09.school_supervision.repository.ExamRepository;
import com.pmarko09.school_supervision.repository.ExamResultRepository;
import com.pmarko09.school_supervision.repository.SubjectRepository;
import com.pmarko09.school_supervision.validation.ExamResultValidation;
import com.pmarko09.school_supervision.validation.ExamValidation;
import com.pmarko09.school_supervision.validation.SubjectValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExamService {

    private final ExamRepository examRepository;
    private final SubjectRepository subjectRepository;
    private final ExamResultRepository examResultRepository;
    private final ExamMapper examMapper;

    public Set<ExamDTO> getAllExams() {
        return examRepository.findAll().stream()
                .map(examMapper::toDto)
                .collect(Collectors.toSet());
    }

    public ExamDTO getExam(Long id) {
        Exam exam = ExamValidation.examExists(examRepository, id);
        return examMapper.toDto(examRepository.save(exam));
    }

    public ExamDTO addExam(Exam exam) {
        ExamValidation.validateExamData(exam);
        return examMapper.toDto(examRepository.save(exam));
    }

    public ExamDTO updateExam(Long id, Exam updatedExam) {
        Exam exam = ExamValidation.examExists(examRepository, id);
        ExamValidation.validateExamData(updatedExam);
        Exam.update(exam, updatedExam);
        return examMapper.toDto(examRepository.save(exam));
    }

    public ExamDTO deleteExam(Long id) {
        Exam exam = ExamValidation.examExists(examRepository, id);
        examRepository.delete(exam);
        return examMapper.toDto(exam);
    }

    public ExamDTO assignSubject(Long examId, Long subjectId) {
        Exam exam = ExamValidation.examExists(examRepository, examId);
        Subject subject = SubjectValidation.subjectExists(subjectRepository, subjectId);

        exam.setSubject(subject);
        subject.getExams().add(exam);
        subjectRepository.save(subject);

        return examMapper.toDto(examRepository.save(exam));
    }

    public ExamDTO assignExamResult(Long examId, Long examResultId) {
        Exam exam = ExamValidation.examExists(examRepository, examId);
        ExamResult examResult = ExamResultValidation.examResultExists(examResultRepository, examResultId);

        exam.setExamResult(examResult);
        examResult.setExam(exam);
        examResultRepository.save(examResult);

        return examMapper.toDto(examRepository.save(exam));
    }

}
