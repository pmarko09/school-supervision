package com.pmarko09.school_supervision.service;

import com.pmarko09.school_supervision.mapper.SubjectMapper;
import com.pmarko09.school_supervision.model.dto.SubjectDTO;
import com.pmarko09.school_supervision.model.entity.Exam;
import com.pmarko09.school_supervision.model.entity.Student;
import com.pmarko09.school_supervision.model.entity.Subject;
import com.pmarko09.school_supervision.model.entity.Teacher;
import com.pmarko09.school_supervision.repository.ExamRepository;
import com.pmarko09.school_supervision.repository.StudentRepository;
import com.pmarko09.school_supervision.repository.SubjectRepository;
import com.pmarko09.school_supervision.repository.TeacherRepository;
import com.pmarko09.school_supervision.validation.ExamValidation;
import com.pmarko09.school_supervision.validation.StudentValidation;
import com.pmarko09.school_supervision.validation.SubjectValidation;
import com.pmarko09.school_supervision.validation.TeacherValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class SubjectService {

    private final SubjectRepository subjectRepository;
    private final TeacherRepository teacherRepository;
    private final ExamRepository examRepository;
    private final StudentRepository studentRepository;
    private final SubjectMapper subjectMapper;

    public Set<SubjectDTO> getAllSubjects() {
        return subjectRepository.findAll().stream()
                .map(subjectMapper::toDto)
                .collect(Collectors.toSet());
    }

    public SubjectDTO getSubject(Long id) {
        Subject subject = SubjectValidation.subjectExists(subjectRepository, id);
        return subjectMapper.toDto(subjectRepository.save(subject));
    }

    public SubjectDTO addSubject(Subject subject) {
        SubjectValidation.validateSubjectData(subject);
        return subjectMapper.toDto(subjectRepository.save(subject));
    }

    public SubjectDTO updateSubject(Long id, Subject updatedSubject) {
        Subject subject = SubjectValidation.subjectExists(subjectRepository, id);
        SubjectValidation.validateSubjectData(updatedSubject);
        Subject.update(subject, updatedSubject);
        return subjectMapper.toDto(subjectRepository.save(subject));
    }

    public SubjectDTO deleteSubject(Long id) {
        Subject subject = SubjectValidation.subjectExists(subjectRepository, id);
        subjectRepository.delete(subject);
        return subjectMapper.toDto(subject);
    }

    public SubjectDTO assignSubjectToTeacher(Long subjectId, Long teacherId) {
        Subject subject = SubjectValidation.subjectExists(subjectRepository, subjectId);
        Teacher teacher = TeacherValidation.teacherExists(teacherRepository, teacherId);

        subject.setTeacher(teacher);
        teacher.setSubject(subject);

        return subjectMapper.toDto(subjectRepository.save(subject));
    }

    public SubjectDTO addExamToSubject(Long subjectId, Long examId) {
        Subject subject = SubjectValidation.subjectExists(subjectRepository, subjectId);
        Exam exam = ExamValidation.examExists(examRepository, examId);

        subject.getExams().add(exam);
        exam.setSubject(subject);

        return subjectMapper.toDto(subjectRepository.save(subject));
    }

    public SubjectDTO addStudentToSubject(Long subjectId, Long studentId) {
        Subject subject = SubjectValidation.subjectExists(subjectRepository, subjectId);
        Student student = StudentValidation.studentExists(studentRepository, studentId);

        subject.getStudents().add(student);
        student.getSubjects().add(subject);

        subjectRepository.save(subject);
        studentRepository.save(student);
        return subjectMapper.toDto(subject);
    }
}
