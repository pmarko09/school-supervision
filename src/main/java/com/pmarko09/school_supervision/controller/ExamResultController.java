package com.pmarko09.school_supervision.controller;

import com.pmarko09.school_supervision.model.dto.ExamResultDTO;
import com.pmarko09.school_supervision.model.entity.ExamResult;
import com.pmarko09.school_supervision.service.ExamResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/examResults")
@RequiredArgsConstructor
public class ExamResultController {

    private final ExamResultService examResultService;

    @GetMapping
    public List<ExamResultDTO> getAllExamResults() {
        return examResultService.getAllExamResults();
    }

    @GetMapping("/{id}")
    public ExamResultDTO getExamResult(@PathVariable Long id) {
        return examResultService.getExamResult(id);
    }

    @PostMapping
    public ExamResultDTO addExamResult(@RequestBody ExamResult examResult) {
        return examResultService.addExamResult(examResult);
    }

    @PutMapping("/{id}")
    public ExamResultDTO updateExamResult(@PathVariable Long id, @RequestBody ExamResult examResult) {
        return examResultService.updateExamResult(id, examResult);
    }

    @DeleteMapping("/{id}")
    public ExamResultDTO deleteExamResult(@PathVariable Long id) {
        return examResultService.deleteExamResult(id);
    }

    @PostMapping("/{examResultId}/student/{studentId}")
    public ExamResultDTO addStudentToExamResult(@PathVariable Long examResultId, @PathVariable Long studentId) {
        return examResultService.assignExamResultToStudent(examResultId, studentId);
    }

    @PostMapping("/{examResultId}/exam/{examId}")
    public ExamResultDTO addExamResultToExamResult(@PathVariable Long examResultId, @PathVariable Long examId) {
        return examResultService.assignExamResultToExam(examResultId, examId);
    }
}
