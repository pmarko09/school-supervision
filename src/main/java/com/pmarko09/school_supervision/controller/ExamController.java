package com.pmarko09.school_supervision.controller;

import com.pmarko09.school_supervision.model.dto.ExamDTO;
import com.pmarko09.school_supervision.model.entity.Exam;
import com.pmarko09.school_supervision.service.ExamService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/exams")
public class ExamController {

    private final ExamService examService;

    @GetMapping
    public Set<ExamDTO> getAllExams() {
        return examService.getAllExams();
    }

    @GetMapping("/{id}")
    public ExamDTO getExam(Long id) {
        return examService.getExam(id);
    }

    @PostMapping
    public ExamDTO addExam(@RequestBody Exam exam) {
        return examService.addExam(exam);
    }

    @PutMapping("/{id}")
    public ExamDTO updateExam(@PathVariable Long id, @RequestBody Exam exam) {
        return examService.updateExam(id, exam);
    }

    @DeleteMapping("/{id}")
    public ExamDTO deleteExam(@PathVariable Long id) {
        return examService.deleteExam(id);
    }

    @PostMapping("/{examId}/subject/{subjectId}")
    public ExamDTO assignExamToSubject(@PathVariable Long examId, @PathVariable Long subjectId) {
        return examService.assignSubject(examId, subjectId);
    }

    @PostMapping("/{examId}/examResult/{examResultId}")
    public ExamDTO assignExamResult(Long examId, Long examResultId) {
        return examService.assignExamResult(examId, examResultId);
    }

}
