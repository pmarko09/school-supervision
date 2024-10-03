package com.pmarko09.school_supervision.service;

import com.pmarko09.school_supervision.exception.exam.ExamNotFoundException;
import com.pmarko09.school_supervision.mapper.ExamMapper;
import com.pmarko09.school_supervision.model.dto.ExamDTO;
import com.pmarko09.school_supervision.model.entity.Exam;
import com.pmarko09.school_supervision.repository.ExamRepository;
import com.pmarko09.school_supervision.repository.ExamResultRepository;
import com.pmarko09.school_supervision.repository.SubjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ExamServiceTest {

    ExamRepository examRepository;
    SubjectRepository subjectRepository;
    ExamResultRepository examResultRepository;
    ExamMapper examMapper;
    ExamService examService;

    @BeforeEach
    void setup() {
        this.examRepository = Mockito.mock(ExamRepository.class);
        this.subjectRepository = Mockito.mock(SubjectRepository.class);
        this.examResultRepository = Mockito.mock(ExamResultRepository.class);
        this.examMapper = Mappers.getMapper(ExamMapper.class);
        this.examService = new ExamService(examRepository, subjectRepository,
                examResultRepository, examMapper);
    }

    @Test
    void getAllExams_DataCorrect_ExamDTOsReturned() {
        //given
        Exam exam = new Exam();
        exam.setId(1L);
        exam.setTime(LocalDateTime.now());

        when(examRepository.findAll()).thenReturn(List.of(exam));

        //when
        List<ExamDTO> result = examService.getAllExams();

        //then
        assertEquals(1L, result.getFirst().getId());
        assertNotNull(result);
    }

    @Test
    void getExam_DataCorrect_ExamDTOReturned() {
        //given
        Exam exam = new Exam();
        exam.setId(1L);
        exam.setTime(LocalDateTime.now());

        when(examRepository.findById(1L)).thenReturn(Optional.of(exam));

        //when
        ExamDTO result = examService.getExam(1L);

        //then
        assertEquals(1L, result.getId());
        assertNotNull(result);
    }

    @Test
    void getExam_ExamNotFound_ExceptionThrown() {
        //given
        Exam exam = new Exam();
        exam.setId(1L);
        exam.setTime(LocalDateTime.now());

        when(examRepository.findById(1L)).thenReturn(Optional.empty());

        //when then
        ExamNotFoundException aThrows = assertThrows(ExamNotFoundException.class, () ->
                examService.getExam(1L));
        assertEquals(aThrows.getMessage(), "Exam with id: 1 not found.");
    }

    @Test
    void addExam_DataCorrect_ExamDTOReturned() {
        //given
        Exam exam = new Exam();
        exam.setId(1L);
        exam.setTime(LocalDateTime.now());

        when(examRepository.save(any())).thenReturn(exam);

        //when
        ExamDTO result = examService.addExam(exam);

        //then
        assertEquals(1L, result.getId());
        assertNotNull(result);
    }
}
