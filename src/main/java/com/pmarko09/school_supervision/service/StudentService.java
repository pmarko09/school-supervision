package com.pmarko09.school_supervision.service;

import com.pmarko09.school_supervision.mapper.StudentMapper;
import com.pmarko09.school_supervision.model.dto.StudentDTO;
import com.pmarko09.school_supervision.model.entity.Student;
import com.pmarko09.school_supervision.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;

    public Set<StudentDTO> getStudents() {
        return studentRepository.findAll().stream()
                .map(studentMapper::toDto)
                .collect(Collectors.toSet());
    }

    public StudentDTO getStudent(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        return studentMapper.toDto(student);
    }

    public StudentDTO addStudent(Student student) {
        return studentMapper.toDto(studentRepository.save(student));
    }

    public StudentDTO updateStudent(Long id, Student updatedStudent) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        Student.update(student, updatedStudent);
        return studentMapper.toDto(studentRepository.save(student));
    }

    public StudentDTO deleteStudent(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        studentRepository.delete(student);
        return studentMapper.toDto(student);
    }

//    public StudentDTO addStudentToSchoolClass(Long studentId, Long schoolClassId) {
//        Student student = studentRepository.findById(studentId)
//                .orElseThrow(() -> new RuntimeException("Student not found"));
//
//        //sprawdzenie czy jest szkola w schoolrepo
//
//
//    }
}
