package com.pmarko09.school_supervision.service;

import com.pmarko09.school_supervision.exception.subject.SubjectNotFoundException;
import com.pmarko09.school_supervision.mapper.SubjectMapper;
import com.pmarko09.school_supervision.model.dto.SubjectDTO;
import com.pmarko09.school_supervision.model.entity.*;
import com.pmarko09.school_supervision.repository.ExamRepository;
import com.pmarko09.school_supervision.repository.StudentRepository;
import com.pmarko09.school_supervision.repository.SubjectRepository;
import com.pmarko09.school_supervision.repository.TeacherRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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

        when(subjectRepository.findAll()).thenReturn(List.of(subject));

        //when
        List<SubjectDTO> result = subjectService.getAllSubjects();

        //then
        assertEquals(1, result.size());
        assertFalse(result.isEmpty());
        assertEquals(1L, result.get(0).getId());
        assertEquals("Polski", result.get(0).getName());
    }

    @Test
    void getSubject_DataCorrect_SubjectDtoReturned() {
        //given
        Subject subject = new Subject();
        subject.setId(1L);
        subject.setName("Polski");

        when(subjectRepository.findById(1L)).thenReturn(Optional.of(subject));

        //when
        SubjectDTO result = subjectService.getSubject(1L);

        //then
        assertEquals("Polski", result.getName());
        assertEquals(1L, result.getId());
    }

    @Test
    void getSubject_SubjectNotFound_ExceptionThrown() {
        //given
        Subject subject = new Subject();
        subject.setId(1L);
        subject.setName("Polski");

        when(subjectRepository.findById(1L)).thenReturn(Optional.empty());

        //when then
        SubjectNotFoundException aThrows = assertThrows(SubjectNotFoundException.class, () ->
                subjectService.getSubject(1L));
        assertEquals(aThrows.getMessage(), "Subject with id: 1 not found");
    }

    @Test
    void addSubject_DataCorrect_SubjectDtoReturned() {
        //given
        Subject subject = new Subject();
        subject.setId(1L);
        subject.setName("Polski");

        when(subjectRepository.save(any())).thenReturn(subject);

        //when
        SubjectDTO result = subjectService.addSubject(subject);

        //then
        assertEquals(1L, result.getId());
        assertEquals("Polski", result.getName());
    }

    @Test
    void updateSubject_DataCorrect_SubjectDtoReturned() {
        //given
        Subject subject = new Subject();
        subject.setId(5L);
        subject.setName("Polski");

        Subject updatedSubject = new Subject();
        updatedSubject.setId(5L);
        updatedSubject.setName("ANG");

        when(subjectRepository.findById(5L)).thenReturn(Optional.of(subject));
        when(subjectRepository.save(subject)).thenReturn(updatedSubject);

        //when
        SubjectDTO result = subjectService.updateSubject(5L, updatedSubject);

        //then
        assertEquals(5L, result.getId());
        assertEquals("ANG", result.getName());
    }

    @Test
    void updateSubject_SubjectNotFound_ExceptionThrown() {
        //given
        Subject subject = new Subject();
        subject.setId(1L);
        subject.setName("Polski");

        when(subjectRepository.findById(1L)).thenReturn(Optional.empty());

        //when then
        SubjectNotFoundException aThrows = assertThrows(SubjectNotFoundException.class, () ->
                subjectService.updateSubject(1L, subject));
        assertEquals(aThrows.getMessage(), "Subject with id: 1 not found");
    }

    @Test
    void deleteSubject_DataCorrect_SubjectDtoReturned() {
        //given
        Subject subject = new Subject();
        subject.setId(5L);
        subject.setName("Polski");
        Student student = new Student();
        subject.setStudents(Set.of(student));

        when(subjectRepository.findById(5L)).thenReturn(Optional.of(subject));

        //when
        SubjectDTO result = subjectService.deleteSubject(5L);

        //then
        assertEquals(5L, result.getId());
        assertEquals("Polski", result.getName());
        verify(subjectRepository, times(1)).delete(subject);
    }

    @Test
    void deleteSubject_SubjectNotFound_ExceptionThrown() {
        //given
        Subject subject = new Subject();
        subject.setId(1L);
        subject.setName("Polski");

        when(subjectRepository.findById(1L)).thenReturn(Optional.empty());

        //when then
        SubjectNotFoundException aThrows = assertThrows(SubjectNotFoundException.class, () ->
                subjectService.deleteSubject(1L));
        assertEquals(aThrows.getMessage(), "Subject with id: 1 not found");
    }

    @Test
    void assignSubjectToTeacher_DataCorrect_SubjectDtoReturned() {
        //given
        Subject subject = new Subject();
        subject.setId(5L);
        subject.setName("Polski");

        Teacher teacher = new Teacher();
        teacher.setId(3L);
        teacher.setFirstname("Jan");
        teacher.setLastname("Kowal");

        when(subjectRepository.findById(5L)).thenReturn(Optional.of(subject));
        when(teacherRepository.findById(3L)).thenReturn(Optional.of(teacher));
        when(subjectRepository.save(any())).thenReturn(subject);

        //when
        SubjectDTO result = subjectService.assignSubjectToTeacher(5L, 3L);

        //then
        assertEquals("Polski", result.getName());
        assertEquals(5L, result.getId());
        assertEquals(3L, result.getTeacherId());
    }

    @Test
    void assignSubjectToTeacher_SubjectNotFound_ExceptionThrown() {
        //given
        Subject subject = new Subject();
        subject.setId(1L);
        subject.setName("Polski");

        when(subjectRepository.findById(1L)).thenReturn(Optional.empty());
        when(teacherRepository.findById(3L)).thenReturn(Optional.empty());

        //when then
        SubjectNotFoundException aThrows = assertThrows(SubjectNotFoundException.class, () ->
                subjectService.assignSubjectToTeacher(1L, 3L));
        assertEquals(aThrows.getMessage(), "Subject with id: 1 not found");
    }

    @Test
    void addExamToSubject_DataCorrect_SubjectDtoReturned() {
        //given
        Subject subject = new Subject();
        subject.setId(1L);
        subject.setName("Polski");
        Student student = new Student();
        subject.setStudents(Set.of(student));

        Exam exam = new Exam();
        exam.setId(2L);

        when(subjectRepository.findById(1L)).thenReturn(Optional.of(subject));
        when(examRepository.findById(2L)).thenReturn(Optional.of(exam));

        when(subjectRepository.save(any())).thenReturn(subject);

        //when
        SubjectDTO result = subjectService.addExamToSubject(1L, 2L);

        //then
        assertEquals(1L, result.getId());
        assertEquals("Polski", result.getName());
        assertTrue(result.getStudentIds().contains(student.getId()));
    }

    @Test
    void addExamToSubject_SubjectNotFound_ExceptionThrown() {
        //given
        Subject subject = new Subject();
        subject.setId(1L);
        subject.setName("Polski");

        when(subjectRepository.findById(1L)).thenReturn(Optional.empty());
        when(examRepository.findById(3L)).thenReturn(Optional.empty());

        //when then
        SubjectNotFoundException aThrows = assertThrows(SubjectNotFoundException.class, () ->
                subjectService.addExamToSubject(1L, 3L));
        assertEquals(aThrows.getMessage(), "Subject with id: 1 not found");
    }

    @Test
    void addStudentToSubject_DataCorrect_SubjectDtoReturned() {
        //given
        Subject subject = new Subject();
        subject.setId(1L);
        subject.setName("Polski");

        Student student = new Student();
        student.setId(1L);
        student.setFirstname("JA");
        student.setLastname("BE");

        when(subjectRepository.findById(1L)).thenReturn(Optional.of(subject));
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));

        when(subjectRepository.save(any())).thenReturn(subject);
        when(studentRepository.save(any())).thenReturn(student);

        //when
        SubjectDTO result = subjectService.addStudentToSubject(1L, 1L);

        //then
        assertEquals(1L, result.getId());
        assertEquals("Polski", result.getName());
        assertTrue(result.getStudentIds().contains(student.getId()));
    }

    @Test
    void addStudentToSubject_SubjectNotFound_ExceptionThrown() {
        //given
        Subject subject = new Subject();
        subject.setId(1L);
        subject.setName("Polski");

        when(subjectRepository.findById(1L)).thenReturn(Optional.empty());
        when(studentRepository.findById(3L)).thenReturn(Optional.empty());

        //when then
        SubjectNotFoundException aThrows = assertThrows(SubjectNotFoundException.class, () ->
                subjectService.addStudentToSubject(1L, 3L));
        assertEquals(aThrows.getMessage(), "Subject with id: 1 not found");
    }
}