package com.pmarko09.school_supervision.controller;

import com.pmarko09.school_supervision.model.dto.TeacherDTO;
import com.pmarko09.school_supervision.model.entity.Teacher;
import com.pmarko09.school_supervision.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/teachers")
public class TeacherController {

    private final TeacherService teacherService;

    @GetMapping
    public List<TeacherDTO> getTeachers() {
        return teacherService.getAllTeachers();
    }

    @GetMapping("/{id}")
    public TeacherDTO getTeacher(@PathVariable Long id) {
        return teacherService.getTeacher(id);
    }

    @PostMapping
    public TeacherDTO addTeacher(@RequestBody Teacher teacher) {
        return teacherService.addTeacher(teacher);
    }

    @PutMapping("/{id}")
    public TeacherDTO updateTeacher(@PathVariable Long id, @RequestBody Teacher updatedTeacher) {
        return teacherService.updateTeacher(id, updatedTeacher);
    }

    @DeleteMapping("/{id}")
    public TeacherDTO deleteTeacher(@PathVariable Long id) {
        return teacherService.deleteTeacher(id);
    }

    @PostMapping("/{teacherId}/subject/{subjectId}")
    public TeacherDTO assignTeacherToSubject(@PathVariable Long teacherId, @PathVariable Long subjectId) {
        return teacherService.assignTeacherToSubject(teacherId, subjectId);
    }
}



