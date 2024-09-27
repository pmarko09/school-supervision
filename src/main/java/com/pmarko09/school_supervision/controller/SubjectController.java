package com.pmarko09.school_supervision.controller;

import com.pmarko09.school_supervision.model.dto.SubjectDTO;
import com.pmarko09.school_supervision.model.entity.Subject;
import com.pmarko09.school_supervision.service.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/subjects")
public class SubjectController {

    private final SubjectService subjectService;

    @GetMapping
    public Set<SubjectDTO> getAllSubjects() {
        return subjectService.getAllSubjects();
    }

    @GetMapping("/{id}")
    public SubjectDTO getSubject(@PathVariable Long id) {
        return subjectService.getSubject(id);
    }

    @PostMapping
    public SubjectDTO addSubject(@RequestBody Subject subject) {
        return subjectService.addSubject(subject);
    }

    @PutMapping("/{id}")
    public SubjectDTO updateSubject(@PathVariable Long id, @RequestBody Subject updatedSubject) {
        return subjectService.updateSubject(id, updatedSubject);
    }

    @DeleteMapping("/{id}")
    public SubjectDTO deleteSubject(@PathVariable Long id) {
        return subjectService.deleteSubject(id);
    }

    @PostMapping("/{subjectId}/teacher/{teacherId}")
    public SubjectDTO assignSubjectToTeacher(@PathVariable Long subjectId, @PathVariable Long teacherId) {
        return subjectService.assignSubjectToTeacher(subjectId, teacherId);
    }

    @PostMapping("/{subjectId}/exams/{examId}")
    public SubjectDTO addExamToSubject(@PathVariable Long subjectId, @PathVariable Long examId) {
        return subjectService.addExamToSubject(subjectId, examId);
    }

    @PostMapping("/{subjectId}/students/{studentId}")
    public SubjectDTO addStudentToSubject(@PathVariable Long subjectId, @PathVariable Long studentId) {
        return subjectService.addStudentToSubject(subjectId, studentId);
    }
}
