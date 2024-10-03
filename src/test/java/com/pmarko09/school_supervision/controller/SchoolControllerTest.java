package com.pmarko09.school_supervision.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pmarko09.school_supervision.model.dto.SchoolClassDTO;
import com.pmarko09.school_supervision.model.entity.SchoolClass;
import com.pmarko09.school_supervision.service.SchoolClassService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
public class SchoolControllerTest {

    @MockBean
    SchoolClassService schoolClassService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void getAllSchoolClasses_DataCorrect_ReturnStatus200() throws Exception {
        //given
        List<SchoolClassDTO> schoolsDtos = List.of(
                SchoolClassDTO.builder()
                        .id(1L)
                        .name("A")
                        .number(25)
                        .build(),
                SchoolClassDTO.builder()
                        .id(2L)
                        .name("B")
                        .number(11)
                        .build()
        );

        when(schoolClassService.getAllSchoolClasses()).thenReturn(schoolsDtos);

        //when then
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/schoolClasses")
                ).andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("A"))
                .andExpect(jsonPath("$[0].number").value(25))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].name").value("B"))
                .andExpect(jsonPath("$[1].number").value(11));
    }

    @Test
    void addSchoolClass_DataCorrect_ReturnStatus200() throws Exception {
        //given
        SchoolClassDTO classDTO = SchoolClassDTO.builder()
                .id(2L)
                .name("B")
                .number(11)
                .build();

        SchoolClass schoolClass = new SchoolClass();
        schoolClass.setId(2L);
        schoolClass.setName("B");
        schoolClass.setNumber(11);

        when(schoolClassService.addSchoolClass(schoolClass)).thenReturn(classDTO);

        //when then
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/schoolClasses")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(schoolClass))
                ).andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.id").value(2L))
                .andExpect(jsonPath("$.name").value("B"))
                .andExpect(jsonPath("$.number").value(11));
    }

    @Test
    void getSchoolClass_DataCorrect_ReturnStatus200() throws Exception {
        //given
        SchoolClassDTO classDTO = SchoolClassDTO.builder()
                .id(2L)
                .name("B")
                .number(11)
                .build();

        when(schoolClassService.getSchoolClass(2L)).thenReturn(classDTO);

        //when then
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/schoolClasses/2")
                ).andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.id").value(2L))
                .andExpect(jsonPath("$.name").value("B"))
                .andExpect(jsonPath("$.number").value(11));
    }

    @Test
    void updateSchoolClass_DataCorrect_ReturnStatus200() throws Exception {
        //given
        SchoolClassDTO classDTO = SchoolClassDTO.builder()
                .id(2L)
                .name("B")
                .number(11)
                .build();

        SchoolClass schoolClass = new SchoolClass();
        schoolClass.setId(2L);
        schoolClass.setName("B");
        schoolClass.setNumber(11);

        when(schoolClassService.updateSchoolClass(2L, schoolClass)).thenReturn(classDTO);

        //when then
        mockMvc.perform(
                        MockMvcRequestBuilders.put("/schoolClasses/2")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(schoolClass))
                ).andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.id").value(2L))
                .andExpect(jsonPath("$.name").value("B"))
                .andExpect(jsonPath("$.number").value(11));
    }

    @Test
    void deleteSchoolClass_DataCorrect_ReturnStatus200() throws Exception {
        //given
        SchoolClassDTO classDTO = SchoolClassDTO.builder()
                .id(2L)
                .name("B")
                .number(11)
                .build();

        when(schoolClassService.deleteSchoolClass(2L)).thenReturn(classDTO);

        //when then
        mockMvc.perform(
                        MockMvcRequestBuilders.delete("/schoolClasses/2")
                ).andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.id").value(2L))
                .andExpect(jsonPath("$.name").value("B"))
                .andExpect(jsonPath("$.number").value(11));
    }

    @Test
    void assignSchoolClassToStudents_DataCorrect_ReturnStatus200() throws Exception {
        //given
        Set<Long> studentsIds = Set.of(3L);

        SchoolClassDTO classDTO = SchoolClassDTO.builder()
                .id(2L)
                .name("B")
                .number(11)
                .studentsIds(studentsIds)
                .build();

        when(schoolClassService.assignSchoolClassToStudents(2L, 3L)).thenReturn(classDTO);

        //when then
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/schoolClasses/2/students/3")
                ).andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.id").value(2L))
                .andExpect(jsonPath("$.name").value("B"))
                .andExpect(jsonPath("$.number").value(11))
                .andExpect(jsonPath("$.studentsIds[0]").value(3L));
    }
}
