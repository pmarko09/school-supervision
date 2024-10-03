package com.pmarko09.school_supervision.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pmarko09.school_supervision.model.dto.TeacherDTO;
import com.pmarko09.school_supervision.model.entity.Teacher;
import com.pmarko09.school_supervision.service.TeacherService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
public class TeacherControllerTest {

    @MockBean
    TeacherService teacherService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void getTeachers_DataCorrect_ReturnStatus200() throws Exception {
        //given
        List<TeacherDTO> teacherDTOS = List.of(
                TeacherDTO.builder()
                        .id(1L)
                        .firstname("Jan")
                        .lastname("Kowal")
                        .email("Jan@")
                        .build(),
                TeacherDTO.builder()
                        .id(2L)
                        .firstname("A")
                        .lastname("B")
                        .email("x@")
                        .build()
        );

        when(teacherService.getAllTeachers()).thenReturn(teacherDTOS);

        //when then
        mockMvc.perform(MockMvcRequestBuilders.get("/teachers"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].firstname").value("Jan"))
                .andExpect(jsonPath("$[0].lastname").value("Kowal"))
                .andExpect(jsonPath("$[0].email").value("Jan@"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].firstname").value("A"))
                .andExpect(jsonPath("$[1].lastname").value("B"))
                .andExpect(jsonPath("$[1].email").value("x@"));
    }

    @Test
    void getTeacher_DataCorrect_ReturnStatus200() throws Exception {
        //given
        TeacherDTO teacherDTO = TeacherDTO.builder()
                .id(1L)
                .firstname("Jan")
                .lastname("Kowal")
                .email("Jan@")
                .build();

        when(teacherService.getTeacher(1L)).thenReturn(teacherDTO);

        //when then
        mockMvc.perform(MockMvcRequestBuilders.get("/teachers/1"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.firstname").value("Jan"))
                .andExpect(jsonPath("$.email").value("Jan@"));
    }

    @Test
    void addTeacher_DataCorrect_ReturnStatus200() throws Exception {
        //given
        Teacher teacher = new Teacher();
        teacher.setFirstname("A");
        teacher.setLastname("B");
        teacher.setEmail("123@");
        teacher.setPassword("444");

        TeacherDTO teacherDTO = TeacherDTO.builder()
                .id(1L)
                .firstname("A")
                .lastname("B")
                .email("123@")
                .build();
//TODO: pytanie o to czemu wpisujac do addTeacher(teacher) nie przechodzilo
        //TODO: w body w metodzie w kontrolerze mam obiekt typu Teacher i tam nie podaje mu ID, a blad od razu rzucalo, ze nie ma nadanego ID
        when(teacherService.addTeacher(any(Teacher.class))).thenReturn(teacherDTO);

        //when then
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/teachers")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(teacher))
                )
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.firstname").value("A"))
                .andExpect(jsonPath("$.lastname").value("B"))
                .andExpect(jsonPath("$.email").value("123@"));
    }

    @Test
    void updateTeacher_DataCorrect_ReturnStatus200() throws Exception {
        //given
        Teacher teacher = new Teacher();
        teacher.setId(1L);
        teacher.setFirstname("A");
        teacher.setLastname("B");
        teacher.setEmail("123@");
        teacher.setPassword("444");

        TeacherDTO teacherDTO = TeacherDTO.builder()
                .id(1L)
                .firstname("A")
                .lastname("B")
                .email("123@")
                .build();

        when(teacherService.updateTeacher(1L, teacher)).thenReturn(teacherDTO);
//TODO: to same pytanie, co wyzej
        //when then
        mockMvc.perform(
                        MockMvcRequestBuilders.put("/teachers/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(teacher))
                )
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.firstname").value("A"))
                .andExpect(jsonPath("$.lastname").value("B"))
                .andExpect(jsonPath("$.email").value("123@"));
    }

    @Test
    void deleteTeacher_DataCorrect_ReturnStatus200() throws Exception {
        //given
        TeacherDTO teacherDTO = TeacherDTO.builder()
                .id(1L)
                .firstname("A")
                .lastname("B")
                .email("123@")
                .build();

        when(teacherService.deleteTeacher(1L)).thenReturn(teacherDTO);

        //when then
        mockMvc.perform(
                        MockMvcRequestBuilders.delete("/teachers/1")
                )
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.firstname").value("A"))
                .andExpect(jsonPath("$.lastname").value("B"))
                .andExpect(jsonPath("$.email").value("123@"));
    }

    @Test
    void assignTeacherToSubject_DataCorrect_ReturnStatus200() throws Exception {
        //given
        TeacherDTO teacherDTO = TeacherDTO.builder()
                .id(1L)
                .firstname("A")
                .lastname("B")
                .email("123@")
                .subjectId(1L)
                .build();

        when(teacherService.assignTeacherToSubject(1L, 1L)).thenReturn(teacherDTO);

        //when then
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/teachers/1/subject/1")
                )
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.firstname").value("A"))
                .andExpect(jsonPath("$.lastname").value("B"))
                .andExpect(jsonPath("$.email").value("123@"))
                .andExpect(jsonPath("$.subjectId").value(1L));
    }
}
