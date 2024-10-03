package com.pmarko09.school_supervision.service;
import com.pmarko09.school_supervision.exception.student.StudentNotFoundException;
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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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
    void getAllStudents_DataCorrect_StudentsDtoReturned() {
        //given
        Student student = new Student();
        student.setId(1L);
        student.setFirstname("Jan");
        student.setLastname("Bebb");
        student.setEmail("aa@");
        student.setCardNumber(123);

        when(studentRepository.findAll()).thenReturn(List.of(student));

        //when
        List<StudentDTO> result = studentService.getStudents();

        //then
        assertEquals(1, result.size());
        assertEquals("Jan", result.get(0).getFirstname());
        assertEquals("Bebb", result.get(0).getLastname());
        assertEquals(123, result.get(0).getCardNumber());
    }
    @Test
    void getStudent_DataCorrect_StudentDtoReturned() {
        //given
        Student student = new Student();
        student.setId(1L);
        student.setFirstname("Jan");
        student.setLastname("Bebb");
        student.setEmail("aa@");
        student.setCardNumber(123);

        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));

        //when
        StudentDTO result = studentService.getStudent(1L);

        //then
        assertEquals("Jan", result.getFirstname());
        assertEquals("Bebb", result.getLastname());
        assertEquals("aa@", result.getEmail());
        assertEquals(123, result.getCardNumber());
    }
    @Test
    void getStudent_StudentNotFound_ExceptionThrown() {
        //given
        Student student = new Student();
        student.setId(1L);
        student.setFirstname("Jan");
        student.setLastname("Bebb");
        student.setEmail("aa@");
        student.setCardNumber(123);

        when(studentRepository.findById(1L)).thenReturn(Optional.empty());

        //when then
        StudentNotFoundException aThrows = assertThrows(StudentNotFoundException.class, () ->
                studentService.getStudent(1L));
        assertEquals(aThrows.getMessage(), "Student with id: 1 not found");
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

        when(studentRepository.save(any())).thenReturn(student);

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
        when(studentRepository.save(student)).thenReturn(updatedStudent);

        //when
        StudentDTO result = studentService.updateStudent(student.getId(), updatedStudent);

        //then
        assertEquals(1L, result.getId());
        assertEquals("Janek", result.getFirstname());
        assertEquals("aa@", result.getEmail());
        assertEquals(1233, result.getCardNumber());
    }
    @Test
    void updateStudent_StudentNotFound_ExceptionThrown() {
        //given
        Student student = new Student();
        student.setId(1L);
        student.setFirstname("Jan");
        student.setLastname("Bebb");
        student.setEmail("aa@");
        student.setCardNumber(123);

        when(studentRepository.findById(1L)).thenReturn(Optional.empty());

        //when then
        StudentNotFoundException aThrows = assertThrows(StudentNotFoundException.class, () ->
                studentService.updateStudent(1L, student));
        assertEquals(aThrows.getMessage(), "Student with id: 1 not found");
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

        //when
        StudentDTO result = studentService.deleteStudent(1L);

        //then
        assertEquals(1L, result.getId());
        assertEquals("Jan", result.getFirstname());
        assertEquals("Bebb", result.getLastname());
        assertEquals(123, result.getCardNumber());
    }
    @Test
    void deleteStudent_StudentNotFound_ExceptionThrown() {
        //given
        Student student = new Student();
        student.setId(1L);
        student.setFirstname("Jan");
        student.setLastname("Bebb");
        student.setEmail("aa@");
        student.setCardNumber(123);

        when(studentRepository.findById(1L)).thenReturn(Optional.empty());

        //when then
        StudentNotFoundException aThrows = assertThrows(StudentNotFoundException.class, () ->
                studentService.deleteStudent(1L));
        assertEquals(aThrows.getMessage(), "Student with id: 1 not found");
    }
    @Test
    void addStudentToSchoolClass_DataCorrect_StudentDtoReturned() {
        //given
        Student student = new Student();
        student.setId(1L);
        student.setFirstname("Jan");
        student.setLastname("Bebb");
        student.setEmail("aa@");
        student.setCardNumber(123);

        SchoolClass schoolClass = new SchoolClass();
        schoolClass.setId(1L);
        schoolClass.setName("PSP");
        schoolClass.setNumber(55);

        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        when(schoolClassRepository.findById(schoolClass.getId())).thenReturn(Optional.of(schoolClass));
        when(studentRepository.save(any())).thenReturn(student);

        //when
        StudentDTO result = studentService.addStudentToSchoolClass(1L, 1L);

        //then
        assertNotNull(result);
        assertEquals(schoolClass, student.getSchoolClass());
        assertEquals(schoolClass.getId(), result.getSchoolClassId());
    }
    @Test
    void addStudentToSchoolClass_StudentNotFound_ExceptionThrown() {
        //given
        Student student = new Student();
        student.setId(1L);
        student.setFirstname("Jan");
        student.setLastname("Bebb");
        student.setEmail("aa@");
        student.setCardNumber(123);

        when(studentRepository.findById(1L)).thenReturn(Optional.empty());
        when(schoolClassRepository.findById(1L)).thenReturn(Optional.empty());

        //when then
        StudentNotFoundException aThrows = assertThrows(StudentNotFoundException.class, () ->
                studentService.addStudentToSchoolClass(1L, 1L));
        assertEquals(aThrows.getMessage(), "Student with id: 1 not found");
    }
    @Test
    void addSubjects_DataCorrect_StudentDtoReturned() {
        //given
        Student student = new Student();
        student.setId(1L);
        student.setFirstname("Jan");
        student.setLastname("Bebb");
        student.setEmail("aa@");
        student.setCardNumber(123);

        Subject subject = new Subject();
        subject.setId(1L);
        subject.setName("Polski");

        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        when(subjectRepository.findById(1L)).thenReturn(Optional.of(subject));
        when(studentRepository.save(any())).thenReturn(student);

        //when
        StudentDTO result = studentService.addSubjects(1L, 1L);

        //then
        assertEquals("Jan", result.getFirstname());
        assertTrue(result.getSubjectIds().contains(1L));
        assertEquals("Bebb", result.getLastname());
    }
    @Test
    void addSubjects_StudentNotFound_ExceptionThrown() {
        //given
        Student student = new Student();
        student.setId(1L);
        student.setFirstname("Jan");
        student.setLastname("Bebb");
        student.setEmail("aa@");
        student.setCardNumber(123);

        when(studentRepository.findById(1L)).thenReturn(Optional.empty());
        when(subjectRepository.findById(1L)).thenReturn(Optional.empty());

        //when then
        StudentNotFoundException aThrows = assertThrows(StudentNotFoundException.class, () ->
                studentService.addSubjects(1L, 1L));
        assertEquals(aThrows.getMessage(), "Student with id: 1 not found");
    }
    @Test
    void addExamResult_DataCorrect_StudentDtoReturned() {
        //given
        Student student = new Student();
        student.setId(1L);
        student.setFirstname("Jan");
        student.setLastname("Bebb");
        student.setEmail("aa@");
        student.setCardNumber(123);

        ExamResult examResult = new ExamResult();
        examResult.setId(2L);
        examResult.setTime(LocalDate.now());
        examResult.setGrade(4.0);

        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        when(examResultRepository.findById(2L)).thenReturn(Optional.of(examResult));

        //when
        StudentDTO result = studentService.addExamResult(1L, 2L);

        //then
        assertEquals(1L, result.getId());
        assertTrue(result.getExamResultsIds().contains(2L));
        assertEquals(123, result.getCardNumber());
    }
}

