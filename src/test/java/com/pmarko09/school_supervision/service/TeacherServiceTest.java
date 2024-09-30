package com.pmarko09.school_supervision.service;

import com.pmarko09.school_supervision.mapper.TeacherMapper;
import com.pmarko09.school_supervision.model.dto.TeacherDTO;
import com.pmarko09.school_supervision.model.entity.Exam;
import com.pmarko09.school_supervision.model.entity.Student;
import com.pmarko09.school_supervision.model.entity.Subject;
import com.pmarko09.school_supervision.model.entity.Teacher;
import com.pmarko09.school_supervision.repository.SubjectRepository;
import com.pmarko09.school_supervision.repository.TeacherRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

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
        teacher.setEmail("A@");
        teacher.setPassword("123");

        when(teacherRepository.findAll()).thenReturn(List.of(teacher));
        TeacherDTO teacherDTO = teacherMapper.toDto(teacher);

        //when
        Set<TeacherDTO> result = teacherService.getAllTeachers();

        //then
        assertEquals(1, result.size());
        assertTrue(result.contains(teacherDTO));
    }

    @Test
    void getTeacher_DataCorrect_teacherDtoReturned() {

        //given
        Teacher teacher = new Teacher();
        teacher.setId(1L);
        teacher.setFirstname("jan");
        teacher.setLastname("A");
        teacher.setEmail("A@");
        teacher.setPassword("123");

        when(teacherRepository.findById(1L)).thenReturn(Optional.of(teacher));
        teacherMapper.toDto(teacher);

        //when
        TeacherDTO result = teacherService.getTeacher(1L);

        //then
        assertEquals("jan", result.getFirstname());
        assertEquals("A", result.getLastname());
        assertEquals("A@", result.getEmail());
    }

    @Test
    void addTeacher_DataCorrect_teacherDtoReturned() {

        //given
        Teacher teacher = new Teacher();
        teacher.setId(1L);
        teacher.setFirstname("jan");
        teacher.setLastname("A");
        teacher.setEmail("A@");
        teacher.setPassword("123");

        when(teacherRepository.save(any())).thenReturn(teacher);
        teacherMapper.toDto(teacher);

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
        Long id = 1L;

        Teacher teacher = new Teacher();
        teacher.setId(1L);
        teacher.setFirstname("jan");
        teacher.setLastname("A");
        teacher.setEmail("A@");
        teacher.setPassword("123");

        Teacher updatedTeacher = new Teacher();
        updatedTeacher.setId(1L);
        updatedTeacher.setFirstname("x");
        updatedTeacher.setLastname("C");
        updatedTeacher.setEmail("X@");
        updatedTeacher.setPassword("123");

        when(teacherRepository.findById(id)).thenReturn(Optional.of(teacher));
        when(teacherRepository.save(any())).thenReturn(updatedTeacher);
        teacherMapper.toDto(teacher);

        //when
        TeacherDTO result = teacherService.updateTeacher(id, updatedTeacher);

        //then
        assertEquals("x", result.getFirstname());
        assertEquals("X@", result.getEmail());
    }

    @Test
    void deleteTeacher_DataCorrect_teacherDtoReturned() {

        //given
        Long id = 2L;

        Teacher teacher = new Teacher();
        teacher.setId(2L);
        teacher.setFirstname("jan");
        teacher.setLastname("A");
        teacher.setEmail("A@");
        teacher.setPassword("123");

        when(teacherRepository.findById(id)).thenReturn(Optional.of(teacher));
        doNothing().when(teacherRepository).delete(teacher);
        teacherMapper.toDto(teacher);

        //when
        TeacherDTO result = teacherService.deleteTeacher(2L);

        //then
        assertEquals(2L, result.getId());
        assertEquals("jan", result.getFirstname());
        assertEquals("A", result.getLastname());
        assertEquals("A@", result.getEmail());
    }

    @Test
    void assignTeacherToSubject_DataCorrect_teacherDtoReturned() {

        //given
        Teacher teacher = new Teacher();
        teacher.setId(2L);
        teacher.setFirstname("jan");
        teacher.setLastname("A");
        teacher.setEmail("A@");
        teacher.setPassword("123");

        Subject subject = new Subject();
        subject.setId(1L);
        subject.setName("Polski");
        Student student = new Student();
        subject.setStudents(Set.of(student));
        Exam exam = new Exam();
        subject.setExams(Set.of(exam));

        when(teacherRepository.findById(2L)).thenReturn(Optional.of(teacher));
        when(subjectRepository.findById(1L)).thenReturn(Optional.of(subject));
        when(teacherRepository.save(any())).thenReturn(teacher);
        teacherMapper.toDto(teacher);

        //when
        TeacherDTO result = teacherService.assignTeacherToSubject(2L, 1L);

        //then
        assertEquals(1L, result.getSubjectId());
    }
}
