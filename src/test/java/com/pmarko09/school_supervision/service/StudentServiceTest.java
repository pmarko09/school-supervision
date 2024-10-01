package com.pmarko09.school_supervision.service;

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
import com.pmarko09.school_supervision.validation.StudentValidation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

public class StudentServiceTest {

    StudentService studentService;
    StudentRepository studentRepository;
    SchoolClassRepository schoolClassRepository;
    SubjectRepository subjectRepository;
    ExamResultRepository examResultRepository;
    StudentMapper studentMapper;

    @BeforeEach
    void setup() {
        this.studentRepository = Mockito.mock(StudentRepository.class);
        this.schoolClassRepository = Mockito.mock(SchoolClassRepository.class);
        this.subjectRepository = Mockito.mock(SubjectRepository.class);
        this.examResultRepository = Mockito.mock(ExamResultRepository.class);
        this.studentMapper = Mappers.getMapper(StudentMapper.class);
        this.studentService = new StudentService(studentRepository, schoolClassRepository,
                subjectRepository, examResultRepository, studentMapper);
    }

    @Test
    void getAllStudents_CorrectData_StudentsDtoReturned() {

        //given
        Student student = new Student();
        student.setId(1L);
        student.setFirstname("Jan");
        student.setLastname("Bebb");
        student.setEmail("aa@");
        student.setCardNumber(123);

        when(studentRepository.findAll()).thenReturn(List.of(student));
        StudentDTO studentDTO = studentMapper.toDto(student);

        //when
        Set<StudentDTO> result = studentService.getStudents();

        //then
        assertEquals(1, result.size());
        assertTrue(result.contains(studentDTO));
    }

    @Test
    void getStudent_DataCorrect_StudenttDtoReturned() {
        //given
        Student student = new Student();
        student.setId(1L);
        student.setFirstname("Jan");
        student.setLastname("Bebb");
        student.setEmail("aa@");
        student.setCardNumber(123);

        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        studentMapper.toDto(student);

        //when
        StudentDTO result = studentService.getStudent(1L);

        //then
        assertEquals("Jan", result.getFirstname());
        assertEquals("Bebb", result.getLastname());
        assertEquals("aa@", result.getEmail());
        assertEquals(123, result.getCardNumber());
    }

    @Test
    void addStudent_DataCorrect_StudentDtoReturned() {
        //given
        Student student = new Student();
        student.setId(1L);
        student.setFirstname("Jan");
        student.setLastname("Bebb");
        student.setEmail("aa@");
        student.setCardNumber(123);

        StudentValidation.validateStudentData(student);

        when(studentRepository.save(any())).thenReturn(student);
        studentMapper.toDto(student);

        //when
        StudentDTO result = studentService.addStudent(student);

        //then
        assertEquals("Jan", result.getFirstname());
        assertEquals("Bebb", result.getLastname());
        assertEquals("aa@", result.getEmail());
        assertEquals(123, result.getCardNumber());
    }

    @Test
    void updateStudent_DataCorrect_StudentDtoReturned() {
        //given
        Student student = new Student();
        student.setId(1L);
        student.setFirstname("Jan");
        student.setLastname("Bebb");
        student.setEmail("aa@");
        student.setCardNumber(123);

        Student updatedStudent = new Student();
        updatedStudent.setId(1L);
        updatedStudent.setFirstname("Janek");
        updatedStudent.setLastname("Bebbbb");
        updatedStudent.setEmail("aa@");
        updatedStudent.setCardNumber(1233);

        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));

        StudentValidation.validateStudentData(updatedStudent);
        Student.update(student, updatedStudent);

        when(studentRepository.save(any())).thenReturn(student);
        StudentDTO studentDTO = studentMapper.toDto(student);

        //when
        StudentDTO result = studentService.updateStudent(student.getId(), updatedStudent);

        //then
        assertEquals(studentDTO.getId(), result.getId());
        assertEquals(studentDTO.getFirstname(), result.getFirstname());
        assertEquals(studentDTO.getEmail(), result.getEmail());
        assertEquals(studentDTO.getCardNumber(), result.getCardNumber());
    }

    @Test
    void deleteStudent_DataCorrect_StudentDtoReturned() {
        //given
        Student student = new Student();
        student.setId(1L);
        student.setFirstname("Jan");
        student.setLastname("Bebb");
        student.setEmail("aa@");
        student.setCardNumber(123);

        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        doNothing().when(studentRepository).delete(student);

        //when
        StudentDTO result = studentService.deleteStudent(1L);

        //then
        assertEquals(1L, result.getId());
        assertEquals("Jan", result.getFirstname());
        assertEquals("Bebb", result.getLastname());
        assertEquals(123, result.getCardNumber());
    }

    @Test
    void addStudentToSchoolClass_DataCorrect_StudentDtoReturned() {
        //given
        Long studentId = 1L;
        Long schoolClassId = 2L;

        Student student = new Student();
        student.setId(studentId);
        student.setFirstname("Jan");
        student.setLastname("Bebb");
        student.setEmail("aa@");
        student.setCardNumber(123);

        SchoolClass schoolClass = new SchoolClass();
        schoolClass.setId(schoolClassId);
        schoolClass.setName("PSP");
        schoolClass.setNumber(55);

        when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));
        when(schoolClassRepository.findById(schoolClass.getId())).thenReturn(Optional.of(schoolClass));

        StudentValidation.schoolAlreadyAssigned(student, schoolClassId);

        when(studentRepository.save(any())).thenReturn(student);

        StudentDTO studentDTO = studentMapper.toDto(student);
        studentDTO.setSchoolClassId(schoolClass.getId());

        //when
        StudentDTO result = studentService.addStudentToSchoolClass(studentId, schoolClass.getId());

        //then
        assertEquals(studentDTO.getSchoolClassId(), result.getSchoolClassId());
        assertEquals(studentDTO.getCardNumber(), result.getCardNumber());
    }

    @Test
    void addSubjects_DataCorrect_StudentDtoReturned() {
        //given
        Long studentId = 1L;
        Long subjectId = 2L;

        Student student = new Student();
        student.setId(studentId);
        student.setFirstname("Jan");
        student.setLastname("Bebb");
        student.setEmail("aa@");
        student.setCardNumber(123);

        Subject subject = new Subject();
        subject.setId(subjectId);
        subject.setName("Polski");

        when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));
        when(subjectRepository.findById(subjectId)).thenReturn(Optional.of(subject));

        StudentValidation.thisSubjectAlreadyAssigned(student, subject);

        when(studentRepository.save(any())).thenReturn(student);

        StudentDTO studentDTO = studentMapper.toDto(student);
        studentDTO.setSubjectIds(Set.of(subject.getId()));

        //when
        StudentDTO result = studentService.addSubjects(studentId, subjectId);

        //then
        assertEquals(studentDTO.getFirstname(), result.getFirstname());
        assertEquals(studentDTO.getSubjectIds(), result.getSubjectIds());
    }

    @Test
    void addExamResult_DataCorrect_StudentDtoReturned() {
        //given
        Long studentId = 1L;
        Long examResultId = 2L;

        Student student = new Student();
        student.setId(studentId);
        student.setFirstname("Jan");
        student.setLastname("Bebb");
        student.setEmail("aa@");
        student.setCardNumber(123);

        ExamResult examResult = new ExamResult();
        examResult.setId(examResultId);
        examResult.setTime(LocalDate.now());
        examResult.setGrade(4.0);

        when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));
        when(examResultRepository.findById(examResultId)).thenReturn(Optional.of(examResult));

        StudentValidation.thisExamResultAlreadyAssigned(student, examResult);

        StudentDTO studentDTO = studentMapper.toDto(student);
        studentDTO.setExamResultsIds(Set.of(examResult.getId()));

        //when
        StudentDTO result = studentService.addExamResult(studentId, examResultId);

        //then
        assertEquals(studentDTO.getId(), result.getId());
        assertEquals(studentDTO.getExamResultsIds(), result.getExamResultsIds());
        assertEquals(studentDTO.getCardNumber(), result.getCardNumber());

    }
}

