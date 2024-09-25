package com.pmarko09.school_supervision.controller;

import com.pmarko09.school_supervision.model.dto.StudentDTO;
import com.pmarko09.school_supervision.model.entity.Student;
import com.pmarko09.school_supervision.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RequestMapping("/students")
@RestController
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @GetMapping
    public Set<StudentDTO> getStudents() {
        return studentService.getStudents();
    }

    @PostMapping
    public StudentDTO addStudent(@RequestBody Student student) {
        return studentService.addStudent(student);
    }

    @GetMapping("/{id}")
    public StudentDTO getStudent(@PathVariable Long id) {
        return studentService.getStudent(id);
    }

    @PutMapping("/{id")
    public StudentDTO updateStudent(@PathVariable Long id, @RequestBody Student updatedStudent) {
        return studentService.updateStudent(id, updatedStudent);
    }

    @DeleteMapping("/{id")
    public StudentDTO deleteStudent(@PathVariable Long id) {
        return studentService.deleteStudent(id);
    }

//    @PostMapping("/{studentId}/schoolClass/{schoolClassId}")
//    public StudentDTO assignStudentToSchoolClass(@PathVariable Long studentId, @PathVariable Long schoolClassId) {
//
//    }
}
