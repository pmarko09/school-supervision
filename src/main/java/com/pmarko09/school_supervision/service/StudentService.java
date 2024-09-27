package com.pmarko09.school_supervision.service;

import com.pmarko09.school_supervision.exception.student.IllegalStudentDataException;
import com.pmarko09.school_supervision.mapper.StudentMapper;
import com.pmarko09.school_supervision.model.dto.StudentDTO;
import com.pmarko09.school_supervision.model.entity.ExamResult;
import com.pmarko09.school_supervision.model.entity.SchoolClass;
import com.pmarko09.school_supervision.model.entity.Student;
import com.pmarko09.school_supervision.model.entity.Subject;
import com.pmarko09.school_supervision.repository.ExamResultRepository;
import com.pmarko09.school_supervision.repository.SchoolClassRepository;
import com.pmarko09.school_supervision.repository.StudentRepository;
import com.pmarko09.school_supervision.repository.SubjectRepository;
import com.pmarko09.school_supervision.validation.ExamResultValidation;
import com.pmarko09.school_supervision.validation.SchoolClassValidation;
import com.pmarko09.school_supervision.validation.StudentValidation;
import com.pmarko09.school_supervision.validation.SubjectValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final SchoolClassRepository schoolClassRepository;
    private final SubjectRepository subjectRepository;
    private final ExamResultRepository examResultRepository;
    private final StudentMapper studentMapper;

    public Set<StudentDTO> getStudents() {
        return studentRepository.findAll().stream()
                .map(studentMapper::toDto)
                .collect(Collectors.toSet());
    }

    public StudentDTO getStudent(Long id) {
        Student student = StudentValidation.studentExists(studentRepository, id);
        return studentMapper.toDto(student);
    }

    public StudentDTO addStudent(Student student) {
        StudentValidation.validateStudentData(student);
        return studentMapper.toDto(studentRepository.save(student));
    }

    public StudentDTO updateStudent(Long id, Student updatedStudent) {
        Student student = StudentValidation.studentExists(studentRepository, id);
        StudentValidation.validateStudentData(updatedStudent);
        Student.update(student, updatedStudent);
        return studentMapper.toDto(studentRepository.save(student));
    }

    public StudentDTO deleteStudent(Long id) {
        Student student = StudentValidation.studentExists(studentRepository, id);
        studentRepository.delete(student);
        return studentMapper.toDto(student);
    }

    public StudentDTO addStudentToSchoolClass(Long studentId, Long schoolClassId) {
        Student student = StudentValidation.studentExists(studentRepository, studentId);
        SchoolClass schoolClass = SchoolClassValidation.schoolClassExists(schoolClassRepository, schoolClassId);

        if (student.getSchoolClass() != null && student.getSchoolClass().getId().equals(schoolClassId)) {
            throw new IllegalStudentDataException("Student is already assigned to this school class.");
        }

        student.setSchoolClass(schoolClass);
        schoolClass.getStudents().add(student);
        studentRepository.save(student);

        return studentMapper.toDto(student);
    }

    public StudentDTO addSubjects(Long studentId, Long subjectId) {
        Student student = StudentValidation.studentExists(studentRepository, studentId);
        Subject subject = SubjectValidation.subjectExists(subjectRepository, subjectId);

        if (student.getSubjects().contains(subject)) {
            throw new IllegalStudentDataException("Student has already been assigned this subject.");
        }

        student.getSubjects().add(subject);
        subject.getStudents().add(student);

        studentRepository.save(student);

        return studentMapper.toDto(student);
    }

    public StudentDTO addExamResult(Long studentId, Long examResultId) {
        Student student = StudentValidation.studentExists(studentRepository, studentId);
        ExamResult examResult = ExamResultValidation.examResultExists(examResultRepository, examResultId);

        student.getExamResults().add(examResult);
        examResult.setStudent(student);

        return studentMapper.toDto(student);
    }
}
