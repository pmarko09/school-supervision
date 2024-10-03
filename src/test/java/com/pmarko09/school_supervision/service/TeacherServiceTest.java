package com.pmarko09.school_supervision.service;

import com.pmarko09.school_supervision.exception.teacher.TeacherNotFoundException;
import com.pmarko09.school_supervision.mapper.TeacherMapper;
import com.pmarko09.school_supervision.model.dto.TeacherDTO;
import com.pmarko09.school_supervision.model.entity.Exam;
import com.pmarko09.school_supervision.model.entity.Student;
import com.pmarko09.school_supervision.model.entity.Subject;
import com.pmarko09.school_supervision.model.entity.Teacher;
import com.pmarko09.school_supervision.repository.SubjectRepository;
import com.pmarko09.school_supervision.repository.TeacherRepository;
import com.pmarko09.school_supervision.validation.SubjectValidation;
import com.pmarko09.school_supervision.validation.TeacherValidation;
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

public class TeacherServiceTest {

    TeacherService teacherService;
    TeacherRepository teacherRepository;
    SubjectRepository subjectRepository;
    TeacherMapper teacherMapper;

    @BeforeEach
    void setup() {
        this.teacherMapper = Mappers.getMapper(TeacherMapper.class);
        this.teacherRepository = Mockito.mock(TeacherRepository.class);
        this.subjectRepository = Mockito.mock(SubjectRepository.class);
        this.teacherService = new TeacherService(teacherRepository, subjectRepository, teacherMapper);
    }

    @Test
    void getTeachers_DataCorrect_TeachersDtoReturned() {
        //given
        Teacher teacher = new Teacher();
        teacher.setId(1L);
        teacher.setFirstname("jan");
        teacher.setLastname("A");

        when(teacherRepository.findAll()).thenReturn(List.of(teacher));

        //when
        List<TeacherDTO> result = teacherService.getAllTeachers();

        //then
        assertEquals(1, result.size());
        assertFalse(result.isEmpty());
        assertEquals(1L, result.getFirst().getId());
        assertEquals("jan", result.getFirst().getFirstname());
    }

    @Test
    void getTeacher_DataCorrect_teacherDtoReturned() {
        //given
        Teacher teacher = new Teacher();
        teacher.setId(1L);
        teacher.setFirstname("jan");
        teacher.setLastname("A");

        when(teacherRepository.findById(1L)).thenReturn(Optional.of(teacher));

        //when
        TeacherDTO result = teacherService.getTeacher(1L);

        //then
        assertEquals("jan", result.getFirstname());
        assertEquals("A", result.getLastname());
        assertEquals(1L, result.getId());
    }

    @Test
    void getTeacher_TeacherNotFound_ExceptionThrown() {
        //given
        Teacher teacher = new Teacher();
        teacher.setId(1L);
        teacher.setFirstname("jan");
        teacher.setLastname("A");

        when(teacherRepository.findById(1L)).thenReturn(Optional.empty());

        //when then
        TeacherNotFoundException aThrows = assertThrows(TeacherNotFoundException.class, () ->
                teacherService.getTeacher(1L));
        assertEquals(aThrows.getMessage(), "Teacher with id: 1 not found");
    }

    @Test
    void addTeacher_DataCorrect_teacherDtoReturned() {
        //given
        Teacher teacher = new Teacher();
        teacher.setId(1L);
        teacher.setFirstname("jan");
        teacher.setLastname("A");
        teacher.setEmail("A@");

        when(teacherRepository.save(any())).thenReturn(teacher);

        //when
        TeacherDTO result = teacherService.addTeacher(teacher);

        //then
        assertEquals(1L, result.getId());
        assertEquals("jan", result.getFirstname());
        assertEquals("A@", result.getEmail());
    }

    @Test
    void updateTeacher_DataCorrect_teacherDtoReturned() {
        //given
        Teacher teacher = new Teacher();
        teacher.setId(1L);
        teacher.setFirstname("jan");
        teacher.setLastname("A");

        Teacher updatedTeacher = new Teacher();
        updatedTeacher.setId(1L);
        updatedTeacher.setFirstname("x");
        updatedTeacher.setLastname("C");

        when(teacherRepository.findById(1L)).thenReturn(Optional.of(teacher));
        when(teacherRepository.save(teacher)).thenReturn(updatedTeacher);

        //when
        TeacherDTO result = teacherService.updateTeacher(1L, updatedTeacher);

        //then
        assertEquals(1L, result.getId());
        assertEquals("x", result.getFirstname());
        assertEquals("C", result.getLastname());
    }

    @Test
    void updateTeacher_TeacherNotFound_ExceptionThrown() {
        //given
        Teacher teacher = new Teacher();
        teacher.setId(1L);
        teacher.setFirstname("jan");
        teacher.setLastname("A");

        when(teacherRepository.findById(1L)).thenReturn(Optional.empty());

        //when then
        TeacherNotFoundException aThrows = assertThrows(TeacherNotFoundException.class, () ->
                teacherService.updateTeacher(1L, teacher));
        assertEquals(aThrows.getMessage(), "Teacher with id: 1 not found");
    }

    @Test
    void deleteTeacher_DataCorrect_teacherDtoReturned() {
        //given
        Teacher teacher = new Teacher();
        teacher.setId(2L);
        teacher.setFirstname("jan");
        teacher.setLastname("A");

        when(teacherRepository.findById(2L)).thenReturn(Optional.of(teacher));

        //when
        TeacherDTO result = teacherService.deleteTeacher(2L);

        //then
        assertEquals(2L, result.getId());
        assertEquals("jan", result.getFirstname());
        assertEquals("A", result.getLastname());
        verify(teacherRepository, times(1)).delete(teacher);
    }

    @Test
    void deleteTeacher_TeacherNotFound_ExceptionThrown() {
        //given
        Teacher teacher = new Teacher();
        teacher.setId(1L);
        teacher.setFirstname("jan");
        teacher.setLastname("A");

        when(teacherRepository.findById(1L)).thenReturn(Optional.empty());

        //when then
        TeacherNotFoundException aThrows = assertThrows(TeacherNotFoundException.class, () ->
                teacherService.deleteTeacher(1L));
        assertEquals(aThrows.getMessage(), "Teacher with id: 1 not found");
    }

    @Test
    void assignTeacherToSubject_DataCorrect_teacherDtoReturned() {
        //given
        Teacher teacher = new Teacher();
        teacher.setId(2L);
        teacher.setFirstname("jan");
        teacher.setLastname("A");

        Subject subject = new Subject();
        subject.setId(1L);
        subject.setName("Polski");

        when(teacherRepository.findById(2L)).thenReturn(Optional.of(teacher));
        when(subjectRepository.findById(1L)).thenReturn(Optional.of(subject));
        when(teacherRepository.save(any())).thenReturn(teacher);

        //when
        TeacherDTO result = teacherService.assignTeacherToSubject(2L, 1L);

        //then
        assertEquals(2L, result.getId());
        assertEquals("jan", result.getFirstname());
        assertEquals("A", result.getLastname());
        assertEquals(teacher.getSubject().getId(), subject.getId());
    }

    @Test
    void assignTeacherToSubject_TeacherNotFound_ExceptionThrown() {
        //given
        Teacher teacher = new Teacher();
        teacher.setId(1L);
        teacher.setFirstname("jan");
        teacher.setLastname("A");

        when(teacherRepository.findById(1L)).thenReturn(Optional.empty());
        when(subjectRepository.findById(1L)).thenReturn(Optional.empty());

        //when then
        TeacherNotFoundException aThrows = assertThrows(TeacherNotFoundException.class, () ->
                teacherService.assignTeacherToSubject(1L, 1L));
        assertEquals(aThrows.getMessage(), "Teacher with id: 1 not found");
    }
}
