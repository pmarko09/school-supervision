package com.pmarko09.school_supervision.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pmarko09.school_supervision.model.dto.StudentDTO;
import com.pmarko09.school_supervision.model.entity.Student;
import com.pmarko09.school_supervision.service.StudentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@SpringBootTest
@AutoConfigureMockMvc
public class StudentControllerTest {

    @MockBean
    StudentService studentService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void getStudents_DataCorrect_ReturnStatus200() throws Exception {
        StudentDTO studentDto = StudentDTO.builder()
                .id(1L).firstname("A").lastname("B")
                .email("A@").cardNumber(123).build();
        StudentDTO studentDto2 = StudentDTO.builder()
                .id(3L).firstname("X").lastname("Y")
                .email("XY@").cardNumber(444).build();
        List<StudentDTO> studentsDTOS = List.of(studentDto, studentDto2);

        when(studentService.getStudents()).thenReturn(studentsDTOS);

        //when then
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/students")
                ).
                andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].firstname").value("A"))
                .andExpect(jsonPath("$[0].lastname").value("B"))
                .andExpect(jsonPath("$[0].email").value("A@"))
                .andExpect(jsonPath("$[0].cardNumber").value(123))
                .andExpect(jsonPath("$[1].id").value(3L))
                .andExpect(jsonPath("$[1].firstname").value("X"))
                .andExpect(jsonPath("$[1].lastname").value("Y"))
                .andExpect(jsonPath("$[1].email").value("XY@"))
                .andExpect(jsonPath("$[1].cardNumber").value(444));
    }

    @Test
    void addStudent_DataCorrect_ReturnStatus200() throws Exception {
        //given
        Student student = new Student();
        student.setId(1L);
        student.setFirstname("Ola");
        student.setLastname("P");
        student.setEmail("o@");
        student.setCardNumber(997);

        StudentDTO studentDto = StudentDTO.builder()
                .id(1L).firstname("Ola").lastname("P")
                .email("o@").cardNumber(997).build();

        when(studentService.addStudent(student)).thenReturn(studentDto);

        //when then
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/students")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(student))
                ).
                andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.firstname").value("Ola"))
                .andExpect(jsonPath("$.lastname").value("P"))
                .andExpect(jsonPath("$.email").value("o@"))
                .andExpect(jsonPath("$.cardNumber").value(997));
    }

    @Test
    void getStudent_DataCorrect_ReturnStatus200() throws Exception {
        //given
        StudentDTO studentDto = StudentDTO.builder()
                .id(1L).firstname("Ola").lastname("P")
                .email("o@").cardNumber(997).build();

        when(studentService.getStudent(1L)).thenReturn(studentDto);

        //when then
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/students/1")
                ).
                andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.firstname").value("Ola"))
                .andExpect(jsonPath("$.lastname").value("P"))
                .andExpect(jsonPath("$.email").value("o@"))
                .andExpect(jsonPath("$.cardNumber").value(997));
    }

    @Test
    void updateStudent_DataCorrect_ReturnStatus200() throws Exception {
        //given
        Student student = new Student();
        student.setId(1L);
        student.setFirstname("Ola");
        student.setLastname("P");
        student.setEmail("o@");

        StudentDTO studentDto = StudentDTO.builder()
                .id(1L).firstname("Ola").lastname("P")
                .email("o@").build();

        when(studentService.updateStudent(1L, student)).thenReturn(studentDto);

        //when then
        mockMvc.perform(
                        MockMvcRequestBuilders.put("/students/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(student))
                ).
                andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.firstname").value("Ola"))
                .andExpect(jsonPath("$.lastname").value("P"))
                .andExpect(jsonPath("$.email").value("o@"));
    }

    @Test
    void deleteStudent_DataCorrect_ReturnStatus200() throws Exception {
        //given
        StudentDTO studentDto = StudentDTO.builder()
                .id(1L).firstname("Ola").lastname("P")
                .email("o@").build();

        when(studentService.deleteStudent(1L)).thenReturn(studentDto);

        //when then
        mockMvc.perform(
                        MockMvcRequestBuilders.delete("/students/1")
                ).
                andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.firstname").value("Ola"))
                .andExpect(jsonPath("$.lastname").value("P"))
                .andExpect(jsonPath("$.email").value("o@"));
    }

    @Test
    void assignStudentToSchoolClass_DataCorrect_ReturnStatus200() throws Exception {
        //given
        StudentDTO studentDto = StudentDTO.builder()
                .id(1L).firstname("Ola").lastname("P")
                .email("o@").schoolClassId(2L).build();

        when(studentService.addStudentToSchoolClass(1L, 2L)).thenReturn(studentDto);

        //when then
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/students/1/schoolClass/2")
                ).
                andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.firstname").value("Ola"))
                .andExpect(jsonPath("$.lastname").value("P"))
                .andExpect(jsonPath("$.email").value("o@"))
                .andExpect(jsonPath("$.schoolClassId").value(2L));
    }

    @Test
    void assignSubjectToStudent_DataCorrect_ReturnStatus200() throws Exception {
        //given
        Set<Long> ids = Set.of(3L);
        StudentDTO studentDto = StudentDTO.builder()
                .id(1L).firstname("Ola").lastname("P")
                .email("o@").subjectIds(ids).build();

        when(studentService.addSubjects(1L, 3L)).thenReturn(studentDto);

        //when then
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/students/1/subjects/3")
                ).
                andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.firstname").value("Ola"))
                .andExpect(jsonPath("$.lastname").value("P"))
                .andExpect(jsonPath("$.email").value("o@"))
                .andExpect(jsonPath("$.subjectIds[0]").value(3L));
    }

    @Test
    void assignExamResultToStudent_DataCorrect_ReturnStatus200() throws Exception {
        //given
        Set<Long> ids = Set.of(2L);
        StudentDTO studentDto = StudentDTO.builder()
                .id(1L).firstname("Ola").lastname("P")
                .email("o@").examResultsIds(ids).build();

        when(studentService.addExamResult(1L, 2L)).thenReturn(studentDto);

        //when then
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/students/1/examResults/2")
                ).
                andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.firstname").value("Ola"))
                .andExpect(jsonPath("$.lastname").value("P"))
                .andExpect(jsonPath("$.email").value("o@"))
                .andExpect(jsonPath("$.examResultsIds[0]").value(2L));
    }
}
