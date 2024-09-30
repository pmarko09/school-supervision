package com.pmarko09.school_supervision.service;

import com.pmarko09.school_supervision.mapper.SubjectMapper;
import com.pmarko09.school_supervision.model.dto.SubjectDTO;
import com.pmarko09.school_supervision.model.entity.Student;
import com.pmarko09.school_supervision.model.entity.Subject;
import com.pmarko09.school_supervision.model.entity.Teacher;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
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
    }

    @Test
    void addSubject_DataCorrect_SubjectDtoReturned() {

        //given
        Subject subject = new Subject();
        subject.setId(1L);
        subject.setName("Polski");
        Student student = new Student();
        subject.setStudents(Set.of(student));

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
    }
}

//further test methods