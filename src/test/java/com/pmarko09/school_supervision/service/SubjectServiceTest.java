package com.pmarko09.school_supervision.service;

import com.pmarko09.school_supervision.mapper.SubjectMapper;
import com.pmarko09.school_supervision.model.dto.SubjectDTO;
import com.pmarko09.school_supervision.model.entity.*;
import com.pmarko09.school_supervision.repository.ExamRepository;
import com.pmarko09.school_supervision.repository.StudentRepository;
import com.pmarko09.school_supervision.repository.SubjectRepository;
import com.pmarko09.school_supervision.repository.TeacherRepository;
import com.pmarko09.school_supervision.validation.ExamValidation;
import com.pmarko09.school_supervision.validation.SubjectValidation;
import com.pmarko09.school_supervision.validation.TeacherValidation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

public class SubjectServiceTest {

    SubjectService subjectService;
    SubjectRepository subjectRepository;
    ExamRepository examRepository;
    TeacherRepository teacherRepository;
    StudentRepository studentRepository;
    SubjectMapper subjectMapper;

    @BeforeEach
    void setup() {
        this.subjectMapper = Mappers.getMapper(SubjectMapper.class);
        this.examRepository = Mockito.mock(ExamRepository.class);
        this.teacherRepository = Mockito.mock(TeacherRepository.class);
        this.subjectRepository = Mockito.mock(SubjectRepository.class);
        this.studentRepository = Mockito.mock(StudentRepository.class);
        this.subjectService = new SubjectService(subjectRepository, examRepository,
                teacherRepository, studentRepository, subjectMapper);
    }

    @Test
    void getAllSubjects_DataCorrect_SubjectsDtoReturned() {

        //given
        Subject subject = new Subject();
        subject.setId(1L);
        subject.setName("Polski");
        Student student = new Student();
        subject.setStudents(Set.of(student));
        Teacher teacher = new Teacher();
        subject.setTeacher(teacher);

        when(subjectRepository.findAll()).thenReturn(List.of(subject));
        SubjectDTO subjectDTO = subjectMapper.toDto(subject);

        //when
        Set<SubjectDTO> result = subjectService.getAllSubjects();

        //then
        assertEquals(1, result.size());
        assertTrue(result.contains(subjectDTO));
    }


    @Test
    void getSubject_DataCorrect_SubjectDtoReturned() {

        //given
        Subject subject = new Subject();
        subject.setId(1L);
        subject.setName("Polski");
        Student student = new Student();
        subject.setStudents(Set.of(student));
        Teacher teacher = new Teacher();
        subject.setTeacher(teacher);

        when(subjectRepository.findById(1L)).thenReturn(Optional.of(subject));
        subjectMapper.toDto(subject);

        //when
        SubjectDTO result = subjectService.getSubject(1L);

        //then
        assertEquals("Polski", result.getName());
        assertEquals(1L, result.getId());
        assertEquals(subject.getTeacher().getId(), result.getTeacherId());
    }

    @Test
    void addSubject_DataCorrect_SubjectDtoReturned() {

        //given
        Subject subject = new Subject();
        subject.setId(1L);
        subject.setName("Polski");
        Student student = new Student();
        subject.setStudents(Set.of(student));

        SubjectValidation.validateSubjectData(subject);
        when(subjectRepository.save(any())).thenReturn(subject);
        subjectMapper.toDto(subject);

        //when
        SubjectDTO result = subjectService.addSubject(subject);

        //then
        assertEquals(1L, result.getId());
        assertEquals("Polski", result.getName());
    }

    @Test
    void updateSubject_DataCorrect_SubjectDtoReturned() {

        //given
        Long id = 5L;

        Subject subject = new Subject();
        subject.setId(5L);
        subject.setName("Polski");
        Student student = new Student();
        subject.setStudents(Set.of(student));

        Subject updatedSubject = new Subject();
        updatedSubject.setId(5L);
        updatedSubject.setName("ANG");
        Student newStudent = new Student();
        updatedSubject.setStudents(Set.of(newStudent));

        when(subjectRepository.findById(id)).thenReturn(Optional.of(subject));
        SubjectValidation.validateSubjectData(updatedSubject);
        Subject.update(subject, updatedSubject);
        when(subjectRepository.save(any())).thenReturn(subject);
        SubjectDTO subjectDTO = subjectMapper.toDto(subject);

        //when
        SubjectDTO result = subjectService.updateSubject(id, updatedSubject);

        //then
        assertEquals(subjectDTO.getId(), result.getId());
        assertEquals(subjectDTO.getName(), result.getName());
        assertEquals(subjectDTO.getTeacherId(), result.getTeacherId());
        assertEquals(subjectDTO.getStudentIds(), result.getStudentIds());
    }

    @Test
    void deleteSubject_DataCorrect_SubjectDtoReturned() {

        //given
        Long id = 5L;

        Subject subject = new Subject();
        subject.setId(5L);
        subject.setName("Polski");
        Student student = new Student();
        subject.setStudents(Set.of(student));

        when(subjectRepository.findById(id)).thenReturn(Optional.of(subject));
        doNothing().when(subjectRepository).delete(subject);
        SubjectDTO subjectDTO = subjectMapper.toDto(subject);

        //when
        SubjectDTO result = subjectService.deleteSubject(id);

        //then
        assertEquals(5L, result.getId());
        assertEquals("Polski", result.getName());
        assertEquals(subjectDTO.getStudentIds(), result.getStudentIds());
    }

    @Test
    void assignSubjectToTeacher_DataCorrect_SubjectDtoReturned() {

        //given
        Long subjectId = 5L;
        Long teacherId = 3L;

        Subject subject = new Subject();
        subject.setId(5L);
        subject.setName("Polski");
        Student student = new Student();
        subject.setStudents(Set.of(student));
        subject.setTeacher(null);

        Teacher teacher = new Teacher();
        teacher.setId(3L);
        teacher.setFirstname("Jan");
        teacher.setLastname("Kowal");
        teacher.setEmail("K@");

        when(subjectRepository.findById(subjectId)).thenReturn(Optional.of(subject));
        when(teacherRepository.findById(teacherId)).thenReturn(Optional.of(teacher));

        SubjectValidation.validateSubjectData(subject);
        TeacherValidation.validateTeacherData(teacher);

        when(subjectRepository.save(any())).thenReturn(subject);

        SubjectDTO subjectDTO = subjectMapper.toDto(subject);
        subjectDTO.setTeacherId(teacher.getId());

        //when
        SubjectDTO result = subjectService.assignSubjectToTeacher(subjectId, teacherId);

        //then
        assertEquals("Polski", result.getName());
        assertEquals(subjectDTO.getId(), result.getId());
        assertEquals(subjectDTO.getTeacherId(), result.getTeacherId());
    }

    @Test
    void addExamToSubject_DataCorrect_SubjectDtoReturned() {
        //given
        Long subjectId = 5L;
        Long examId = 3L;

        Subject subject = new Subject();
        subject.setId(subjectId);
        subject.setName("Polski");
        Student student = new Student();
        subject.setStudents(Set.of(student));

        Exam exam = new Exam();
        exam.setId(examId);
        exam.setTime(LocalDateTime.now());
        exam.setSubject(null);

        when(subjectRepository.findById(subjectId)).thenReturn(Optional.of(subject));
        when(examRepository.findById(examId)).thenReturn(Optional.of(exam));

        ExamValidation.validateExamData(exam);
        SubjectValidation.thisExamAlreadyAssigned(subject, exam);

        when(subjectRepository.save(any())).thenReturn(subject);

        SubjectDTO subjectDTO = subjectMapper.toDto(subject);
        subjectDTO.setExamIds(Set.of(exam.getId()));

        //when
        SubjectDTO result = subjectService.addExamToSubject(subjectId, examId);

        //then
        assertEquals(5L, result.getId());
        assertEquals("Polski", result.getName());
        assertEquals(subjectDTO.getExamIds(), result.getExamIds());
    }

    @Test
    void addStudentToSubject_DataCorrect_SubjectDtoReturned() {
        //given
        Long subjectId = 5L;
        Long studentId = 3L;

        Subject subject = new Subject();
        subject.setId(subjectId);
        subject.setName("Polski");

        Student student = new Student();
        student.setId(studentId);
        student.setFirstname("JA");
        student.setLastname("BE");
        student.setEmail("AA@");

        when(subjectRepository.findById(subjectId)).thenReturn(Optional.of(subject));
        when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));

        SubjectValidation.thisStudentAlreadyAssigned(subject, student);

        when(subjectRepository.save(any())).thenReturn(subject);

        SubjectDTO subjectDTO = subjectMapper.toDto(subject);
        subjectDTO.setStudentIds(Set.of(student.getId()));

        //when
        SubjectDTO result = subjectService.addStudentToSubject(subjectId, studentId);

        //then
        assertEquals(5L, result.getId());
        assertEquals("Polski", result.getName());
        assertEquals(subjectDTO.getStudentIds(), result.getStudentIds());
    }
}

//further test methods