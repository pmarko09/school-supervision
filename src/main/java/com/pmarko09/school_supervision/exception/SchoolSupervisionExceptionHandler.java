package com.pmarko09.school_supervision.exception;

import com.pmarko09.school_supervision.exception.exam.ExamNotFoundException;
import com.pmarko09.school_supervision.exception.exam.IllegalExamDataException;
import com.pmarko09.school_supervision.exception.schoolClass.IllegalSchoolClassDataException;
import com.pmarko09.school_supervision.exception.schoolClass.SchoolClassNotFoundException;
import com.pmarko09.school_supervision.exception.student.IllegalStudentDataException;
import com.pmarko09.school_supervision.exception.student.StudentNotFoundException;
import com.pmarko09.school_supervision.exception.subject.IllegalSubjectDataException;
import com.pmarko09.school_supervision.exception.subject.SubjectNotFoundException;
import com.pmarko09.school_supervision.exception.teacher.IllegalTeacherDataException;
import com.pmarko09.school_supervision.exception.teacher.TeacherNotFoundException;
import com.pmarko09.school_supervision.model.dto.ErrorMessageDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class SchoolSupervisionExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ExamNotFoundException.class)
    protected ResponseEntity<Object> handleExamNotFound(ExamNotFoundException ex, WebRequest webRequest) {
        ErrorMessageDTO bodyResponse = new ErrorMessageDTO(ex.getMessage(), LocalDateTime.now(), HttpStatus.NOT_FOUND);
        return handleExceptionInternal(ex, bodyResponse, new HttpHeaders(), HttpStatus.NOT_FOUND, webRequest);
    }

    @ExceptionHandler(IllegalExamDataException.class)
    protected ResponseEntity<Object> handleIllegalExamData(IllegalExamDataException ex, WebRequest webRequest) {
        ErrorMessageDTO bodyResponse = new ErrorMessageDTO(ex.getMessage(), LocalDateTime.now(), HttpStatus.BAD_REQUEST);
        return handleExceptionInternal(ex, bodyResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, webRequest);
    }

    @ExceptionHandler(SchoolClassNotFoundException.class)
    protected ResponseEntity<Object> handleSchoolClassNotFound(SchoolClassNotFoundException ex, WebRequest webRequest) {
        ErrorMessageDTO bodyResponse = new ErrorMessageDTO(ex.getMessage(), LocalDateTime.now(), HttpStatus.NOT_FOUND);
        return handleExceptionInternal(ex, bodyResponse, new HttpHeaders(), HttpStatus.NOT_FOUND, webRequest);
    }

    @ExceptionHandler(IllegalSchoolClassDataException.class)
    protected ResponseEntity<Object> handleIllegalSchoolClassData(IllegalSchoolClassDataException ex, WebRequest webRequest) {
        ErrorMessageDTO bodyResponse = new ErrorMessageDTO(ex.getMessage(), LocalDateTime.now(), HttpStatus.BAD_REQUEST);
        return handleExceptionInternal(ex, bodyResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, webRequest);
    }

    @ExceptionHandler(StudentNotFoundException.class)
    protected ResponseEntity<Object> handleStudentNotFound(StudentNotFoundException ex, WebRequest webRequest) {
        ErrorMessageDTO bodyResponse = new ErrorMessageDTO(ex.getMessage(), LocalDateTime.now(), HttpStatus.NOT_FOUND);
        return handleExceptionInternal(ex, bodyResponse, new HttpHeaders(), HttpStatus.NOT_FOUND, webRequest);
    }

    @ExceptionHandler(IllegalStudentDataException.class)
    protected ResponseEntity<Object> handleIllegalStudentData(IllegalStudentDataException ex, WebRequest webRequest) {
        ErrorMessageDTO bodyResponse = new ErrorMessageDTO(ex.getMessage(), LocalDateTime.now(), HttpStatus.BAD_REQUEST);
        return handleExceptionInternal(ex, bodyResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, webRequest);
    }

    @ExceptionHandler(SubjectNotFoundException.class)
    protected ResponseEntity<Object> handleSubjectNotFound(SubjectNotFoundException ex, WebRequest webRequest) {
        ErrorMessageDTO bodyResponse = new ErrorMessageDTO(ex.getMessage(), LocalDateTime.now(), HttpStatus.NOT_FOUND);
        return handleExceptionInternal(ex, bodyResponse, new HttpHeaders(), HttpStatus.NOT_FOUND, webRequest);
    }

    @ExceptionHandler(IllegalSubjectDataException.class)
    protected ResponseEntity<Object> handleIllegalSubjectData(IllegalSubjectDataException ex, WebRequest webRequest) {
        ErrorMessageDTO bodyResponse = new ErrorMessageDTO(ex.getMessage(), LocalDateTime.now(), HttpStatus.BAD_REQUEST);
        return handleExceptionInternal(ex, bodyResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, webRequest);
    }

    @ExceptionHandler(TeacherNotFoundException.class)
    protected ResponseEntity<Object> handleTeacherNotFound(TeacherNotFoundException ex, WebRequest webRequest) {
        ErrorMessageDTO bodyResponse = new ErrorMessageDTO(ex.getMessage(), LocalDateTime.now(), HttpStatus.NOT_FOUND);
        return handleExceptionInternal(ex, bodyResponse, new HttpHeaders(), HttpStatus.NOT_FOUND, webRequest);
    }

    @ExceptionHandler(IllegalTeacherDataException.class)
    protected ResponseEntity<Object> handleIllegalTeacherData(IllegalTeacherDataException ex, WebRequest webRequest) {
        ErrorMessageDTO bodyResponse = new ErrorMessageDTO(ex.getMessage(), LocalDateTime.now(), HttpStatus.BAD_REQUEST);
        return handleExceptionInternal(ex, bodyResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, webRequest);
    }
}
