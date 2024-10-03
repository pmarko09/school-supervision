package com.pmarko09.school_supervision.service;

import com.pmarko09.school_supervision.exception.schoolClass.SchoolClassNotFoundException;
import com.pmarko09.school_supervision.mapper.SchoolClassMapper;
import com.pmarko09.school_supervision.model.dto.SchoolClassDTO;
import com.pmarko09.school_supervision.model.entity.SchoolClass;
import com.pmarko09.school_supervision.model.entity.Student;
import com.pmarko09.school_supervision.repository.SchoolClassRepository;
import com.pmarko09.school_supervision.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;

import javax.swing.text.html.Option;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class SchoolClassServiceTest {

    SchoolClassRepository schoolClassRepository;
    StudentRepository studentRepository;
    SchoolClassMapper schoolClassMapper;
    SchoolClassService schoolClassService;

    @BeforeEach
    void setup() {
        this.schoolClassMapper = Mappers.getMapper(SchoolClassMapper.class);
        this.schoolClassRepository = Mockito.mock(SchoolClassRepository.class);
        this.studentRepository = Mockito.mock(StudentRepository.class);
        this.schoolClassService = new SchoolClassService(schoolClassRepository,
                studentRepository, schoolClassMapper);
    }

    @Test
    void getAllSchoolClasses_DataCorrect_SchoolClassDTOsReturned() {
        //given
        SchoolClass schoolClass = new SchoolClass();
        schoolClass.setId(1L);
        schoolClass.setName("PSP");
        schoolClass.setNumber(22);

        when(schoolClassRepository.findAll()).thenReturn(List.of(schoolClass));

        //when
        List<SchoolClassDTO> result = schoolClassService.getAllSchoolClasses();

        //then
        assertNotNull(result);
        assertEquals(1L, result.get(0).getId());
        assertEquals("PSP", result.get(0).getName());
    }

    @Test
    void getSchoolClass_DataCorrect_SchoolClassDTOsReturned() {
        //given
        SchoolClass schoolClass = new SchoolClass();
        schoolClass.setId(1L);
        schoolClass.setName("PSP");
        schoolClass.setNumber(22);

        when(schoolClassRepository.findById(1L)).thenReturn(Optional.of(schoolClass));

        //when
        SchoolClassDTO result = schoolClassService.getSchoolClass(1L);

        //then
        assertEquals(1L, result.getId());
        assertEquals("PSP", result.getName());
        assertEquals(22, result.getNumber());
    }

    @Test
    void getSchoolClass_SchoolClassNotFound_ExceptionThrown() {
        //given
        SchoolClass schoolClass = new SchoolClass();
        schoolClass.setId(1L);
        schoolClass.setName("PSP");
        schoolClass.setNumber(22);

        when(schoolClassRepository.findById(1L)).thenReturn(Optional.empty());

        //when then
        SchoolClassNotFoundException aThrows = assertThrows(SchoolClassNotFoundException.class, () ->
                schoolClassService.getSchoolClass(1L));
        assertEquals(aThrows.getMessage(), "School class with id: 1 not found");
    }

    @Test
    void addSchoolClasses_DataCorrect_SchoolClassDTOsReturned() {
        //given
        SchoolClass schoolClass = new SchoolClass();
        schoolClass.setId(1L);
        schoolClass.setName("PSP");
        schoolClass.setNumber(22);

        when(schoolClassRepository.save(schoolClass)).thenReturn(schoolClass);

        //when
        SchoolClassDTO result = schoolClassService.addSchoolClass(schoolClass);

        //then
        assertEquals(1L, result.getId());
        assertEquals("PSP", result.getName());
        assertEquals(22, result.getNumber());
    }

    @Test
    void updateSchoolClass_DataCorrect_SchoolClassDTOsReturned() {
        //given
        SchoolClass schoolClass = new SchoolClass();
        schoolClass.setId(1L);
        schoolClass.setName("PSP");
        schoolClass.setNumber(22);

        //given
        SchoolClass updatedSchoolClass = new SchoolClass();
        updatedSchoolClass.setId(1L);
        updatedSchoolClass.setName("PSPX");
        updatedSchoolClass.setNumber(223);

        when(schoolClassRepository.findById(1L)).thenReturn(Optional.of(schoolClass));
        when(schoolClassRepository.save(schoolClass)).thenReturn(updatedSchoolClass);

        //when
        SchoolClassDTO result = schoolClassService.updateSchoolClass(1L, updatedSchoolClass);

        //then
        assertEquals(1L, result.getId());
        assertEquals("PSPX", result.getName());
        assertEquals(223, result.getNumber());
    }

    @Test
    void updateSchoolClass_SchoolClassNotFound_ExceptionThrown() {
        //given
        SchoolClass schoolClass = new SchoolClass();
        schoolClass.setId(1L);
        schoolClass.setName("PSP");
        schoolClass.setNumber(22);

        when(schoolClassRepository.findById(1L)).thenReturn(Optional.empty());

        //when then
        SchoolClassNotFoundException aThrows = assertThrows(SchoolClassNotFoundException.class, () ->
                schoolClassService.updateSchoolClass(1L, schoolClass));
        assertEquals(aThrows.getMessage(), "School class with id: 1 not found");
    }

    @Test
    void deleteSchoolClasses_DataCorrect_SchoolClassDTOsReturned() {
        //given
        SchoolClass schoolClass = new SchoolClass();
        schoolClass.setId(1L);
        schoolClass.setName("PSP");
        schoolClass.setNumber(22);

        when(schoolClassRepository.findById(1L)).thenReturn(Optional.of(schoolClass));

        //when
        SchoolClassDTO result = schoolClassService.deleteSchoolClass(1L);

        //then
        assertEquals(1L, result.getId());
        assertEquals("PSP", result.getName());
        assertEquals(22, result.getNumber());
        verify(schoolClassRepository, times(1)).delete(schoolClass);
    }

    @Test
    void deleteSchoolClass_SchoolClassNotFound_ExceptionThrown() {
        //given
        SchoolClass schoolClass = new SchoolClass();
        schoolClass.setId(1L);
        schoolClass.setName("PSP");
        schoolClass.setNumber(22);

        when(schoolClassRepository.findById(1L)).thenReturn(Optional.empty());

        //when then
        SchoolClassNotFoundException aThrows = assertThrows(SchoolClassNotFoundException.class, () ->
                schoolClassService.deleteSchoolClass(1L));
        assertEquals(aThrows.getMessage(), "School class with id: 1 not found");
    }

    @Test
    void assignSchoolClassToStudents_DataCorrect_SchoolClassDTOsReturned() {
        //given
        SchoolClass schoolClass = new SchoolClass();
        schoolClass.setId(1L);
        schoolClass.setName("PSP");
        schoolClass.setNumber(22);

        Student student = new Student();
        student.setId(1L);
        student.setFirstname("A");
        student.setLastname("B");



        when(schoolClassRepository.findById(1L)).thenReturn(Optional.of(schoolClass));
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        when(schoolClassRepository.save(any())).thenReturn(schoolClass);

        //when
        SchoolClassDTO result = schoolClassService.assignSchoolClassToStudents(1L, 1L);

        //then
        assertEquals(1L, result.getId());
        assertNotNull(result.getStudentsIds());
    }

    @Test
    void assignSchoolClassToStudents_SchoolClassNotFound_ExceptionThrown() {
        //given
        SchoolClass schoolClass = new SchoolClass();
        schoolClass.setId(1L);
        schoolClass.setName("PSP");
        schoolClass.setNumber(22);

        Student student = new Student();
        student.setId(1L);
        student.setFirstname("A");
        student.setLastname("B");

        when(schoolClassRepository.findById(1L)).thenReturn(Optional.empty());
        when(studentRepository.findById(1L)).thenReturn(Optional.empty());

        //when then
        SchoolClassNotFoundException aThrows = assertThrows(SchoolClassNotFoundException.class, () ->
                schoolClassService.assignSchoolClassToStudents(1L, 1L));
        assertEquals(aThrows.getMessage(), "School class with id: 1 not found");
    }
}
